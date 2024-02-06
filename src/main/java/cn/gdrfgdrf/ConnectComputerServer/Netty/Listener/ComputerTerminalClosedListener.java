package cn.gdrfgdrf.ConnectComputerServer.Netty.Listener;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ComputerTerminalClosedEvent;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author gdrfgdrf
 */
@Component
public class ComputerTerminalClosedListener implements ApplicationListener<ComputerTerminalClosedEvent> {
    @Async
    @Override
    public void onApplicationEvent(@NonNull ComputerTerminalClosedEvent event) {
        ControllerUser controllerUser = event.getControllerUser();
        ComputerProto.TerminalClosedPacket message = event.getMessage();

        Writer.write(
                controllerUser.getChannel(),
                message,
                null,
                ResultEnum.NETTY_COMPUTER_TERMINAL_CLOSED
        );

        ComputerUser computerUser = controllerUser.getComputerUser();
        computerUser.setControllerUser(null);
        computerUser.setExchangeConnectionRsaKey(false);
        controllerUser.setComputerUser(null);
        controllerUser.setExchangeConnectionRsaKey(false);
    }
}

package cn.gdrfgdrf.ConnectComputerServer.Netty.Listener;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ControllerTerminalClosedEvent;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author gdrfgdrf
 */
@Component
public class ControllerTerminalClosedListener implements ApplicationListener<ControllerTerminalClosedEvent> {
    @Async
    @Override
    public void onApplicationEvent(@NonNull ControllerTerminalClosedEvent event) {
        ComputerUser computerUser = event.getComputerUser();
        ControllerProto.TerminalClosedPacket message = event.getMessage();

        Writer.write(
                computerUser.getChannel(),
                message,
                null,
                ResultEnum.NETTY_CONTROLLER_TERMINAL_CLOSED
        );

        ControllerUser controllerUser = computerUser.getControllerUser();
        controllerUser.setComputerUser(null);
        controllerUser.setExchangeConnectionRsaKey(false);
        computerUser.setControllerUser(null);
        computerUser.setExchangeConnectionRsaKey(false);
    }
}

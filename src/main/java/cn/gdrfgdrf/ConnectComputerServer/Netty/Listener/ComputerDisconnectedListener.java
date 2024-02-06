package cn.gdrfgdrf.ConnectComputerServer.Netty.Listener;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ComputerDisconnectedEvent;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author gdrfgdrf
 */
@Component
public class ComputerDisconnectedListener implements ApplicationListener<ComputerDisconnectedEvent> {
    @Async
    @Override
    public void onApplicationEvent(@NonNull ComputerDisconnectedEvent event) {
        ControllerUser controllerUser = event.getControllerUser();

        Writer.write(
                controllerUser.getChannel(),
                ComputerProto.ComputerDisconnectedPacket.newBuilder()
                        .build(),
                null,
                ResultEnum.NETTY_COMPUTER_DISCONNECTED
        );
    }
}

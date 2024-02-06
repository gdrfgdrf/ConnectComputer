package cn.gdrfgdrf.ConnectComputerServer.Netty.Listener;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ControllerDisconnectedEvent;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author gdrfgdrf
 */
@Component
public class ControllerDisconnectedListener implements ApplicationListener<ControllerDisconnectedEvent> {
    @Async
    @Override
    public void onApplicationEvent(@NonNull ControllerDisconnectedEvent event) {
        ComputerUser computerUser = event.getComputerUser();

        Writer.write(
                computerUser.getChannel(),
                ControllerProto.ControllerDisconnectedPacket.newBuilder()
                        .build(),
                null,
                ResultEnum.NETTY_CONTROLLER_DISCONNECTED
        );
    }
}

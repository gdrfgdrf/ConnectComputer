package cn.gdrfgdrf.ConnectComputerServer.Netty.Listener;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ComputerOnlineEvent;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author gdrfgdrf
 */
@Component
public class ComputerOnlineListener implements ApplicationListener<ComputerOnlineEvent> {
    @Async
    @Override
    public void onApplicationEvent(@NonNull ComputerOnlineEvent event) {
        ComputerUser computerUser = event.getComputerUser();
        List<ControllerUser> controllerUsers = NettyServer.getInstance().getControlllerUserList(computerUser.getId());
        if (controllerUsers == null) {
            return;
        }

        ComputerProto.ComputerOnlinePacket packet = ComputerProto.ComputerOnlinePacket.newBuilder()
                .setId(computerUser.getComputerId())
                .build();

        controllerUsers.forEach(controllerUser -> {
            Writer.write(
                    controllerUser.getChannel(),
                    packet,
                    null,
                    ResultEnum.NETTY_COMPUTER_ONLINE,
                    computerUser.getName()
            );
        });
    }
}

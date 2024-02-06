package cn.gdrfgdrf.ConnectComputerServer.Netty.Listener;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ConnectComputerSuccessEvent;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import lombok.NonNull;
import org.springframework.context.ApplicationListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

/**
 * @author gdrfgdrf
 */
@Component
public class ConnectComputerSuccessListener implements ApplicationListener<ConnectComputerSuccessEvent> {
    @Async
    @Override
    public void onApplicationEvent(@NonNull ConnectComputerSuccessEvent event) {
        ComputerUser computerUser = event.getComputerUser();

        Writer.write(
                computerUser.getChannel(),
                ComputerSuccessProto.ComputerIsControlledPacket.newBuilder()
                        .build(),
                null,
                ResultEnum.NETTY_COMPUTER_IS_CONNECTED
        );
    }
}

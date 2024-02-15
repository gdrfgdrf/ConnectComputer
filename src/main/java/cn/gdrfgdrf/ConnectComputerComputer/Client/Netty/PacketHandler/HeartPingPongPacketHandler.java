package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Connection.Heart.HeartProto;
import com.google.protobuf.Message;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
@PacketHandler(support = {
        HeartProto.HeartPingPacket.class
})
public class HeartPingPongPacketHandler implements BasePacketHandler {
    @Override
    public void handle(
            Channel channel,
            BaseProto.Packet packet,
            Message message
    ) throws Exception {
        HeartProto.HeartPongPacket heartPongPacket = HeartProto.HeartPongPacket.newBuilder()
                .build();
        NettyClient.INSTANCE.getSender().send(heartPongPacket);
    }
}

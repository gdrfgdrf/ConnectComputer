package cn.gdrfgdrf.ConnectComputerServer.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Handler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Connection.Heart.HeartProto;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author gdrfgdrf
 */
@Component
@Handler(support = HeartProto.HeartPongPacket.class)
public class HeartPongPacketHandler implements PacketHandler<HeartProto.HeartPongPacket> {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, HeartProto.HeartPongPacket message) throws Exception {

    }
}

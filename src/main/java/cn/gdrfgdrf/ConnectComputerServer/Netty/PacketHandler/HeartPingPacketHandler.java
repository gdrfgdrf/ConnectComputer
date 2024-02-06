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
@Handler(support = HeartProto.HeartPingPacket.class)
public class HeartPingPacketHandler implements PacketHandler<HeartProto.HeartPingPacket> {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, HeartProto.HeartPingPacket message) throws Exception {

    }
}

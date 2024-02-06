package cn.gdrfgdrf.ConnectComputerServer.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Handler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Connection.Goodbye.GoodbyeProto;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author gdrfgdrf
 */
@Component
@Handler(support = GoodbyeProto.GoodbyePacket.class)
public class GoodbyePacketHandler implements PacketHandler<GoodbyeProto.GoodbyePacket> {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, GoodbyeProto.GoodbyePacket message) throws Exception {
        Writer.write(
                channel,
                GoodbyeProto.GoodbyePacket.newBuilder()
                        .build(),
                null,
                ResultEnum.NETTY_GOODBYE
        );
        channel.close();
    }
}

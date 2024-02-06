package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Handler;

import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.Protobuf.BaseProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author gdrfgdrf
 */
public class ClientVersionHandler extends MessageToMessageEncoder<BaseProto.Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, BaseProto.Packet packet, List<Object> out) throws Exception {
        out.add(
                packet.toBuilder()
                        .setClientVersion(Constants.VERSION)
                        .build()
        );
    }
}

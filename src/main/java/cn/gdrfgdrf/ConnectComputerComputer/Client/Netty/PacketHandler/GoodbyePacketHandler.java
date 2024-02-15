package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Connection.Goodbye.GoodbyeProto;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyGoodbyePacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import com.google.protobuf.Message;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
@PacketHandler(support = {
        GoodbyeProto.GoodbyePacket.class
})
public class GoodbyePacketHandler implements BasePacketHandler {
    @Override
    public void handle(
            Channel channel,
            BaseProto.Packet packet,
            Message message
    ) throws Exception {
        NettyClient.INSTANCE.setNeedReconnect(false);
        NettyGoodbyePacketCallback callback = NettyCallbackCollection.INSTANCE.getNettyCallback(GoodbyePacketHandler.class);
        callback.onGoodbyePacket(PacketMessage.createByMessage(packet, message));
    }
}
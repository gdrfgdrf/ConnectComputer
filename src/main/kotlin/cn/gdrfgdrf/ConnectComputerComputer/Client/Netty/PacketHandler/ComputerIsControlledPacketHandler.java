package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyComputerIsControlledPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import com.google.protobuf.Message;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
@PacketHandler(support = {
        ComputerSuccessProto.ComputerIsControlledPacket.class
})
public class ComputerIsControlledPacketHandler implements BasePacketHandler {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, Message message) throws Exception {
        NettyComputerIsControlledPacketCallback callback =
                NettyCallbackCollection.INSTANCE.getNettyCallback(ComputerIsControlledPacketHandler.class);
        callback.onComputerIsControlledPacket(PacketMessage.createByMessage(packet, message));
    }
}

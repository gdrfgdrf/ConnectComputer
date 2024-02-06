package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyControllerDisconnectedPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import com.google.protobuf.Message;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
@PacketHandler(support = {
        ControllerProto.ControllerDisconnectedPacket.class,
})
public class ControllerDisconnectedPacketHandler implements BasePacketHandler {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, Message message) throws Exception {
        NettyControllerDisconnectedPacketCallback callback =
                NettyCallbackCollection.INSTANCE.getNettyCallback(ControllerDisconnectedPacketHandler.class);
        callback.onControllerDisconnectedPacket(PacketMessage.createByMessage(packet, message));
    }
}

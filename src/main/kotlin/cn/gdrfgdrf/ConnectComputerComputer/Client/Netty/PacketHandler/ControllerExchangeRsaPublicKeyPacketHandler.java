package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyControllerExchangeRsaPublicKeyPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import cn.gdrfgdrf.Protobuf.Action.Controller.Security.ControllerSecurityProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import com.google.protobuf.Message;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
@PacketHandler(support = {
        ControllerSecurityProto.ExchangeRsaPublicKeyPacket.class
})
public class ControllerExchangeRsaPublicKeyPacketHandler implements BasePacketHandler {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, Message message) throws Exception {
        NettyControllerExchangeRsaPublicKeyPacketCallback callback =
                NettyCallbackCollection.INSTANCE.getNettyCallback(ControllerExchangeRsaPublicKeyPacketHandler.class);
        callback.onExchangeRsaPublicKeyPacket(PacketMessage.createByMessage(packet, message));
    }
}

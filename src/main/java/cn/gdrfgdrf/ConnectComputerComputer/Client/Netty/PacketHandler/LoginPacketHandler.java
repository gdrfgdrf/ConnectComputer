package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.Protobuf.Action.Account.AccountErrorProto;
import cn.gdrfgdrf.Protobuf.Action.Account.AccountSuccessProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyLoginPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import com.google.protobuf.Message;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
@PacketHandler(support = {
        AccountSuccessProto.LoginSuccessPacket.class,
        AccountErrorProto.LoginFailedPacket.class
})
public class LoginPacketHandler implements BasePacketHandler {
    @Override
    public void handle(
            Channel channel,
            BaseProto.Packet packet,
            Message message
    ) throws Exception {
        NettyLoginPacketCallback callback = NettyCallbackCollection.INSTANCE.getNettyCallback(LoginPacketHandler.class);
        PacketMessage packetMessage = PacketMessage.createByMessage(packet, message);

        if (message instanceof AccountSuccessProto.LoginSuccessPacket) {
            callback.onLoginSuccess(packetMessage);
            return;
        }

        callback.onLoginFailed(packetMessage);
    }
}


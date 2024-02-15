package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.Protobuf.Action.Account.AccountErrorProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerErrorProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.Security.ComputerSecurityProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerErrorProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerSuccessProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.Security.ControllerSecurityProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.ExternalErrorProto;
import cn.gdrfgdrf.Protobuf.Common.InternalErrorProto;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyDefaultPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import cn.gdrfgdrf.Protobuf.Connection.Heart.HeartProto;
import com.google.protobuf.Message;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
@PacketHandler(support = {
        ExternalErrorProto.ConditionNotSatisfiedPacket.class,

        ControllerSuccessProto.ConnectComputerSuccessPacket.class,
        ControllerErrorProto.OnlySingleConnectionPacket.class,
        ControllerErrorProto.ComputerNotOnlinePacket.class,

        ComputerSecurityProto.ExchangeRsaPublicKeyPacket.class,
        ComputerSecurityProto.AesKeyReceivedPacket.class,

        AccountErrorProto.TokenExpiredPacket.class,
        AccountErrorProto.TokenInvalidPacket.class,
        ExternalErrorProto.ForcedOfflinePacket.class,

        ExternalErrorProto.NotFoundUserPacket.class,

        InternalErrorProto.ErrorPacket.class,
        ExternalErrorProto.ParameterErrorPacket.class,
        HeartProto.HeartTimeoutPacket.class
})
public class DefaultPacketHandler implements BasePacketHandler {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, Message message) throws Exception {
        NettyDefaultPacketCallback callback = NettyCallbackCollection.INSTANCE.getNettyCallback(Object.class);
        callback.onPacket(PacketMessage.createByMessage(packet, message));
    }
}




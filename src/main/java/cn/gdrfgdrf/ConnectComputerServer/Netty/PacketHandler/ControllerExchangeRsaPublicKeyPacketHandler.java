package cn.gdrfgdrf.ConnectComputerServer.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.ClientVersion;
import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Handler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Enum.ConditionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.Protobuf.Action.Controller.Security.ControllerSecurityProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

/**
 * @author gdrfgdrf
 */
@Component
@Condition(
        condition = {
                ConditionEnum.AES_KEY_HAS_BEEN_GENERATED,
                ConditionEnum.LOGGED_IN,
                ConditionEnum.LOGIN_MODE_IS_CONTROLLER,
                ConditionEnum.CONTROLLER_IS_CONTROLLING,
                ConditionEnum.CONTROLLER_EXCHANGED_CONNECTION_RSA_KEY
        },
        notSatisfiedResult = {
                ResultEnum.NETTY_AES_KEY_HAS_NOT_BEEN_GENERATED,
                ResultEnum.NETTY_NEED_LOGIN,
                ResultEnum.NETTY_LOGIN_MODE_IS_NOT_CONTROLLER,
                ResultEnum.NETTY_CONTROLLER_IS_NOT_CONTROLLING,
                ResultEnum.NETTY_CONTROLLER_ALREADY_EXCHANGED_RSA_KEY
        },
        need = {
                true,
                true,
                true,
                true,
                false
        }
)
@Handler(support = ControllerSecurityProto.ExchangeRsaPublicKeyPacket.class)
@ClientVersion(version = VersionEnum.v1_0_0)
public class ControllerExchangeRsaPublicKeyPacketHandler implements PacketHandler<ControllerSecurityProto.ExchangeRsaPublicKeyPacket> {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, ControllerSecurityProto.ExchangeRsaPublicKeyPacket message) throws Exception {
        ControllerUser controllerUser = NettyServer.getInstance().getUser(channel);
        ComputerUser computerUser = controllerUser.getComputerUser();

        Writer.write(
                computerUser.getChannel(),
                message,
                packet.getRequestId(),
                ResultEnum.NETTY_CONTROLLER_EXCHANGE_RSA_KEY
        );

        controllerUser.setExchangeConnectionRsaKey(true);
    }
}

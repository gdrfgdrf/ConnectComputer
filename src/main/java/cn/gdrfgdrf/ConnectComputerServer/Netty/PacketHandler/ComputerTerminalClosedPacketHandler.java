package cn.gdrfgdrf.ConnectComputerServer.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.ClientVersion;
import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Holder.SpringContextHolder;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Handler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Enum.ConditionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ComputerTerminalClosedEvent;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
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
                ConditionEnum.LOGIN_MODE_IS_COMPUTER,
                ConditionEnum.COMPUTER_IS_CONTROLLED
        },
        notSatisfiedResult = {
                ResultEnum.NETTY_AES_KEY_HAS_NOT_BEEN_GENERATED,
                ResultEnum.NETTY_NEED_LOGIN,
                ResultEnum.NETTY_LOGIN_MODE_IS_NOT_COMPUTER,
                ResultEnum.NETTY_COMPUTER_IS_NOT_CONTROLLED
        }
)
@Handler(support = ComputerProto.TerminalClosedPacket.class)
@ClientVersion(version = VersionEnum.v1_0_0)
public class ComputerTerminalClosedPacketHandler implements PacketHandler<ComputerProto.TerminalClosedPacket> {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, ComputerProto.TerminalClosedPacket message) throws Exception {
        ComputerUser computerUser = NettyServer.getInstance().getUser(channel);
        ControllerUser controllerUser = computerUser.getControllerUser();

        ComputerTerminalClosedEvent computerTerminalClosedEvent = new ComputerTerminalClosedEvent(
                this,
                controllerUser,
                message
        );
        SpringContextHolder.publishEvent(computerTerminalClosedEvent);
    }
}

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
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ControllerTerminalClosedEvent;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
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
                ConditionEnum.CONTROLLER_IS_CONTROLLING
        },
        notSatisfiedResult = {
                ResultEnum.NETTY_AES_KEY_HAS_NOT_BEEN_GENERATED,
                ResultEnum.NETTY_NEED_LOGIN,
                ResultEnum.NETTY_LOGIN_MODE_IS_NOT_CONTROLLER,
                ResultEnum.NETTY_CONTROLLER_IS_NOT_CONTROLLING
        }
)
@Handler(support = ControllerProto.TerminalClosedPacket.class)
@ClientVersion(version = VersionEnum.v1_0_0)
public class ControllerTerminalClosedPacketHandler implements PacketHandler<ControllerProto.TerminalClosedPacket> {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, ControllerProto.TerminalClosedPacket message) throws Exception {
        ControllerUser controllerUser = NettyServer.getInstance().getUser(channel);
        ComputerUser computerUser = controllerUser.getComputerUser();

        ControllerTerminalClosedEvent terminalClosedEvent = new ControllerTerminalClosedEvent(
                this,
                computerUser,
                message
        );
        SpringContextHolder.publishEvent(terminalClosedEvent);
    }
}

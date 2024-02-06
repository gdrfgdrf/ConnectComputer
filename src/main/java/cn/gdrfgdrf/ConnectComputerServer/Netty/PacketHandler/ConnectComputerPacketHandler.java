package cn.gdrfgdrf.ConnectComputerServer.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.ClientVersion;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.ComputerEntity;
import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Holder.SpringContextHolder;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Handler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Enum.ConditionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ConnectComputerSuccessEvent;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Exception.IllegalParameterNettyException;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerErrorProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerErrorProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerSuccessProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.ExternalErrorProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IComputerEntityService;
import io.netty.channel.Channel;
import org.springframework.beans.factory.annotation.Autowired;
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
                ResultEnum.NETTY_CONTROLLER_IS_CONTROLLING
        },
        need = {
                true,
                true,
                true,
                false
        }
)
@Handler(support = ControllerProto.ConnectComputerPacket.class)
@ClientVersion(version = VersionEnum.v1_0_0)
public class ConnectComputerPacketHandler implements PacketHandler<ControllerProto.ConnectComputerPacket> {
    private final IComputerEntityService computerEntityService;

    @Autowired
    public ConnectComputerPacketHandler(IComputerEntityService computerEntityService) {
        this.computerEntityService = computerEntityService;
    }

    @Override
    public void handle(Channel channel, BaseProto.Packet packet, ControllerProto.ConnectComputerPacket message) throws Exception {
        ControllerUser controllerUser = NettyServer.getInstance().getUser(channel);
        ComputerEntity computer = computerEntityService.findByOwnerAndComputerId(controllerUser.getId(), message.getId());

        if (computer == null) {
            throw new IllegalParameterNettyException(
                    ResultEnum.NETTY_NOT_FOUND_COMPUTER,
                    ExternalErrorProto.NotFoundComputerPacket.class,
                    packet.getRequestId(),
                    false
            );
        }

        ComputerUser computerUser = NettyServer.getInstance().getComputerUser(controllerUser.getId(), message.getId());
        if (computerUser == null) {
            throw new IllegalParameterNettyException(
                    ResultEnum.NETTY_COMPUTER_NOT_ONLINE,
                    ControllerErrorProto.ComputerNotOnlinePacket.class,
                    packet.getRequestId(),
                    false
            );
        }
        if (computerUser.getControllerUser() != null) {
            throw new IllegalParameterNettyException(
                    ResultEnum.NETTY_ONLY_SINGLE_CONNECTION,
                    ControllerErrorProto.OnlySingleConnectionPacket.class,
                    packet.getRequestId(),
                    false
            );
        }

        controllerUser.setComputerUser(computerUser);
        computerUser.setControllerUser(controllerUser);

        Writer.write(
                channel,
                ControllerSuccessProto.ConnectComputerSuccessPacket.newBuilder()
                        .build(),
                packet.getRequestId(),
                ResultEnum.NETTY_CONNECT_COMPUTER_SUCCESS
        );

        ConnectComputerSuccessEvent event = new ConnectComputerSuccessEvent(this, computerUser);
        SpringContextHolder.publishEvent(event);
    }
}

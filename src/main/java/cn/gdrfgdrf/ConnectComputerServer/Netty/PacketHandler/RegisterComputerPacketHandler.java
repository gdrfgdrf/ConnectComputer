package cn.gdrfgdrf.ConnectComputerServer.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.ClientVersion;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.ComputerEntity;
import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Holder.SpringContextHolder;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Handler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Enum.ConditionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ComputerOnlineEvent;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerErrorProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IComputerEntityService;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.NettyServerUtils;
import cn.gdrfgdrf.Protobuf.BaseProto;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author gdrfgdrf
 */
@Slf4j
@Component
@Condition(
        condition = {
                ConditionEnum.AES_KEY_HAS_BEEN_GENERATED,
                ConditionEnum.LOGGED_IN,
                ConditionEnum.LOGIN_MODE_IS_COMPUTER
        },
        notSatisfiedResult = {
                ResultEnum.NETTY_AES_KEY_HAS_NOT_BEEN_GENERATED,
                ResultEnum.NETTY_NEED_LOGIN,
                ResultEnum.NETTY_LOGIN_MODE_IS_NOT_COMPUTER
        }
)
@Handler(support = ComputerProto.RegisterComputerPacket.class)
@ClientVersion(version = VersionEnum.v1_0_0)
public class RegisterComputerPacketHandler implements PacketHandler<ComputerProto.RegisterComputerPacket> {
    private final IComputerEntityService computerEntityService;

    @Autowired
    public RegisterComputerPacketHandler(IComputerEntityService computerEntityService) {
        this.computerEntityService = computerEntityService;
    }

    @Override
    public void handle(Channel channel, BaseProto.Packet packet, ComputerProto.RegisterComputerPacket message) throws Exception {
        ComputerUser computerUser = NettyServer.getInstance().getUser(channel);
        ComputerEntity computerEntity = null;

        if (message.getId() != -1) {
            computerEntity = computerEntityService.findByOwnerAndComputerId(computerUser.getId(), message.getId());
        }

        if (computerEntity == null) {
            computerEntity = new ComputerEntity();
            computerEntity.setId(null);
            computerEntity.setOwnerId(computerUser.getId());
            computerEntity.setName(message.getName());

            computerEntityService.save(computerEntity);
            computerUser.setComputerId(computerEntity.getId());
            computerUser.setName(computerEntity.getName());

            Writer.write(
                    channel,
                    ComputerSuccessProto.RegisterComputerSuccessPacket.newBuilder()
                            .setId(computerEntity.getId())
                            .build(),
                    packet.getRequestId(),
                    ResultEnum.NETTY_REGISTER_COMPUTER_SUCCESS
            );
        } else {
            if (NettyServer.getInstance().computerOnline(computerUser.getId(), computerEntity.getId())) {
                ComputerUser forceRemoveComputerUser = NettyServer.getInstance().getComputerUser(
                        computerUser.getId(), computerEntity.getId()
                );
                NettyServerUtils.forceRemoveConnection(forceRemoveComputerUser.getChannel());
            }
            computerUser.setComputerId(computerEntity.getId());
            computerUser.setName(computerEntity.getName());

            Writer.write(
                    channel,
                    ComputerErrorProto.AlreadyRegisterComputerPacket.newBuilder()
                            .build(),
                    packet.getRequestId(),
                    ResultEnum.NETTY_ALREADY_REGISTER_COMPUTER
            );
        }

        ComputerOnlineEvent computerOnlineEvent = new ComputerOnlineEvent(this, computerUser);
        SpringContextHolder.publishEvent(computerOnlineEvent);
    }
}

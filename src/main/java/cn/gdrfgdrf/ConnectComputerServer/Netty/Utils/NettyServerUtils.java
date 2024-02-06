package cn.gdrfgdrf.ConnectComputerServer.Netty.Utils;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Holder.SpringContextHolder;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.NettyUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ComputerDisconnectedEvent;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ComputerOfflineEvent;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Event.ControllerDisconnectedEvent;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.Protobuf.Common.ExternalErrorProto;
import cn.gdrfgdrf.Protobuf.Connection.Goodbye.GoodbyeProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUserEntityService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;

/**
 * @author gdrfgdrf
 */
public class NettyServerUtils {
    private NettyServerUtils() {}

    public static void forceRemoveConnection(
            Channel channel
    ) {
        Writer.write(
                channel,
                ExternalErrorProto.ForcedOfflinePacket.newBuilder()
                        .build(),
                null,
                ResultEnum.NETTY_FORCED_OFFLINE
        );

        ChannelFuture channelFuture = Writer.write(
                channel,
                GoodbyeProto.GoodbyePacket.newBuilder()
                        .build(),
                null,
                ResultEnum.NETTY_GOODBYE
        );
        if (channelFuture != null) {
            channelFuture.addListener(listener -> {
                channel.close();
            });
        }
    }

    public static void removeConnection(
            IUserEntityService userEntityService,
            Channel channel,
            Object eventSource
    ) {
        removeKey(channel);

        NettyUser nettyUser = NettyServer.getInstance().getUser(channel);
        if (nettyUser != null) {
            if (nettyUser instanceof ControllerUser controllerUser) {
                NettyServer.getInstance().removeControllerUser(controllerUser);
                unbindForControllerUser(controllerUser, eventSource);
            }
            if (nettyUser instanceof ComputerUser computerUser &&
                    computerUser.getComputerId() != null) {
                NettyServer.getInstance().removeComputerUser(computerUser);
                unbindForComputerUser(computerUser, eventSource);
                notifyComputerOffline(eventSource, computerUser);
            }

            updateDatabase(userEntityService, nettyUser.getId());
        }

        NettyServer.getInstance().userSignOut(channel);
    }

    private static void removeKey(Channel channel) {
        NettyServer.getInstance().removeAesKey(channel);
    }

    private static void updateDatabase(IUserEntityService userEntityService, Integer id) {
        UserEntity user = userEntityService.getById(id);
        if (user != null) {
            user.setTokenEntities(null);
            userEntityService.updateById(user);
        }
    }

    private static void unbindForControllerUser(ControllerUser controllerUser, Object eventSource) {
        if (controllerUser.getComputerUser() == null) {
            return;
        }
        ComputerUser computerUser = controllerUser.getComputerUser();
        computerUser.setControllerUser(null);
        computerUser.setExchangeConnectionRsaKey(false);
        notifyComputerControllerDisconnected(eventSource, computerUser);

        controllerUser.setComputerUser(null);
    }

    private static void unbindForComputerUser(ComputerUser computerUser, Object eventSource) {
        if (computerUser.getControllerUser() == null) {
            return;
        }
        ControllerUser controllerUser = computerUser.getControllerUser();
        controllerUser.setComputerUser(null);
        controllerUser.setExchangeConnectionRsaKey(false);
        notifyControllerComputerDisconnected(eventSource, controllerUser);

        computerUser.setControllerUser(null);
    }

    private static void notifyControllerComputerDisconnected(Object eventSource, ControllerUser controllerUser) {
        ComputerDisconnectedEvent computerDisconnectedEvent = new ComputerDisconnectedEvent(eventSource, controllerUser);
        SpringContextHolder.publishEvent(computerDisconnectedEvent);
    }

    private static void notifyComputerControllerDisconnected(Object eventSource, ComputerUser computerUser) {
        ControllerDisconnectedEvent controllerDisconnectedEvent = new ControllerDisconnectedEvent(eventSource, computerUser);
        SpringContextHolder.publishEvent(controllerDisconnectedEvent);
    }

    private static void notifyComputerOffline(Object eventSource, ComputerUser computerUser) {
        ComputerOfflineEvent computerOfflineEvent = new ComputerOfflineEvent(eventSource, computerUser);
        SpringContextHolder.publishEvent(computerOfflineEvent);
    }
}

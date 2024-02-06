package cn.gdrfgdrf.ConnectComputerServer.Netty;

import cn.gdrfgdrf.ConnectComputerServer.Holder.SpringContextHolder;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.NettyUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.AesKey;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Initializer.ServerInitializer;
import cn.gdrfgdrf.Protobuf.Connection.Goodbye.GoodbyeProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gdrfgdrf
 */
@Component
public class NettyServer {
    private static NettyServer INSTANCE;

    public static NettyServer getInstance() {
        if (INSTANCE == null) {
            INSTANCE = SpringContextHolder.getBean(NettyServer.class);
        }

        return INSTANCE;
    }

    public static int PORT;
    public static int SO_BACK_LOG;

    public final Map<Class<? extends GeneratedMessageV3>, PacketHandler> HANDLER = new HashMap<>();

    private final Map<Channel, AesKey> ALL_AES_KEY = new ConcurrentHashMap<>();

    private final Map<Channel, Long> ALL_NOT_LOGGED_IN_CONNECTION = new ConcurrentHashMap<>();
    private final Map<Channel, NettyUser> LOGIN_USER = new ConcurrentHashMap<>();
    private final Map<Integer, List<ControllerUser>> LOGIN_CONTROLLER_USER = new ConcurrentHashMap<>();
    private final Map<Integer, List<ComputerUser>> LOGIN_COMPUTER_USER = new ConcurrentHashMap<>();

    public void addAesKey(Channel channel, AesKey aesKey) {
        ALL_AES_KEY.put(channel, aesKey);
    }

    public void removeAesKey(Channel channel) {
        ALL_AES_KEY.remove(channel);
    }

    public AesKey getAesKey(Channel channel) {
        return ALL_AES_KEY.get(channel);
    }

    public boolean containsAesKey(Channel channel) {
        return ALL_AES_KEY.containsKey(channel);
    }

    public void addNotLoggedInConnection(Channel channel, long timestamp) {
        ALL_NOT_LOGGED_IN_CONNECTION.put(channel, timestamp);
    }

    public void userLogin(Channel channel, NettyUser user) {
        ALL_NOT_LOGGED_IN_CONNECTION.remove(channel);
        LOGIN_USER.put(channel, user);
    }

    public void userSignOut(Channel channel) {
        ALL_NOT_LOGGED_IN_CONNECTION.remove(channel);
        LOGIN_USER.remove(channel);
    }

    public <T extends NettyUser> T getUser(Channel channel) {
        return (T) LOGIN_USER.get(channel);
    }

    public boolean containsUser(Channel channel) {
        return LOGIN_USER.containsKey(channel);
    }

    public void addControllerUser(ControllerUser controllerUser) {
        Integer id = controllerUser.getId();
        LOGIN_CONTROLLER_USER.computeIfAbsent(id, k -> new LinkedList<>());
        LOGIN_CONTROLLER_USER.get(id).add(controllerUser);
    }

    public void removeControllerUser(ControllerUser controllerUser) {
        Integer id = controllerUser.getId();
        if (!LOGIN_CONTROLLER_USER.containsKey(id)) {
            return;
        }

        List<ControllerUser> list = LOGIN_CONTROLLER_USER.get(id);
        list.remove(controllerUser);
        if (list.isEmpty()) {
            LOGIN_CONTROLLER_USER.remove(id);
        }
    }

    public ControllerUser getController(Integer id) {
        if (!LOGIN_CONTROLLER_USER.containsKey(id)) {
            return null;
        }
        return LOGIN_CONTROLLER_USER.get(id)
                .stream()
                .filter(controllerUser -> {
                    if (controllerUser.getId() == null) {
                        return false;
                    }
                    return controllerUser.getId().equals(id);
                })
                .findFirst()
                .orElse(null);
    }

    public List<ControllerUser> getControlllerUserList(Integer id) {
        return LOGIN_CONTROLLER_USER.get(id);
    }

    public void addComputerUser(ComputerUser computerUser) {
        Integer id = computerUser.getId();
        LOGIN_COMPUTER_USER.computeIfAbsent(id, k -> new LinkedList<>());
        LOGIN_COMPUTER_USER.get(id).add(computerUser);
    }

    public void removeComputerUser(ComputerUser computerUser) {
        Integer id = computerUser.getId();
        if (!LOGIN_COMPUTER_USER.containsKey(id)) {
            return;
        }

        List<ComputerUser> list = LOGIN_COMPUTER_USER.get(id);
        list.remove(computerUser);
        if (list.isEmpty()) {
            LOGIN_COMPUTER_USER.remove(id);
        }
    }

    public ComputerUser getComputerUser(Integer id, Integer computerId) {
        if (!LOGIN_COMPUTER_USER.containsKey(id)) {
            return null;
        }
        return LOGIN_COMPUTER_USER.get(id)
                .stream()
                .filter(computerUser -> {
                    if (computerUser.getComputerId() == null) {
                        return false;
                    }
                    return computerUser.getComputerId().equals(computerId);
                })
                .findFirst()
                .orElse(null);
    }

    public List<ComputerUser> getComputerUserList(Integer id) {
        return LOGIN_COMPUTER_USER.get(id);
    }

    public boolean computerOnline(Integer id, Integer computerId) {
        List<ComputerUser> computerUsers = getComputerUserList(id);
        if (computerUsers == null) {
            return false;
        }

        return computerUsers.stream().anyMatch(computerUser -> {
            if (computerUser.getComputerId() == null) {
                return false;
            }
            return computerUser.getComputerId().equals(computerId);
        });
    }

    public void run() throws InterruptedException {
        EventLoopGroup boss = new NioEventLoopGroup();
        EventLoopGroup worker = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(boss, worker);
        serverBootstrap.channel(NioServerSocketChannel.class);
        serverBootstrap.childHandler(new ServerInitializer());
        serverBootstrap.option(ChannelOption.SO_BACKLOG, SO_BACK_LOG);
        serverBootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);

        ChannelFuture channelFuture = serverBootstrap.bind(PORT).sync();
        channelFuture.channel().closeFuture().sync();
    }

    @Scheduled(fixedDelay = 5000)
    public void removeNotLoggedInConnection() {
        if (ALL_NOT_LOGGED_IN_CONNECTION.isEmpty()) {
            return;
        }

        ALL_NOT_LOGGED_IN_CONNECTION.forEach((channel, connectTimestamp) -> {
            Long currentTimestamp = System.currentTimeMillis();

            if ((currentTimestamp - connectTimestamp) >= 5000) {
                try {
                    ChannelFuture channelFuture = Writer.write(
                            channel,
                            GoodbyeProto.GoodbyePacket.newBuilder()
                                    .build(),
                            null,
                            ResultEnum.NETTY_GOODBYE
                    );
                    if (channelFuture != null) {
                        channelFuture.sync();
                    }

                    channel.close();
                } catch (Exception e) {
                    channel.close();
                }
            }
        });
    }
}

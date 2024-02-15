package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Initializer.ClientInitializer;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Result.ResultEnum;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.Synchronization.SyncFuture;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.ServerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.NettyConnectionStateChangeCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.NetworkUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import cn.gdrfgdrf.Protobuf.Security.SecurityProto;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.tuple.Pair;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gdrfgdrf
 */
@Slf4j
public enum NettyClient {
    INSTANCE;

    @Setter
    @Getter
    private ServerInfo serverInfo;

    @Getter
    private final Map<Class<? extends GeneratedMessageV3>, BasePacketHandler> handlerMap = new HashMap<>();

    private final Bootstrap bootstrap;
    private EventLoopGroup group;
    private ChannelFuture channelFuture;
    @Setter
    @Getter
    private boolean needReconnect = true;

    private NettyConnectionStateChangeCallback callback;
    @Getter
    private final PacketSender sender;

    {
        bootstrap = new Bootstrap();
        group = new NioEventLoopGroup();

        bootstrap.group(group);
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.handler(new ClientInitializer());

        callback = null;
        sender = new PacketSender();
    }

    private final ChannelFutureListener futureListener = future -> {
        if (callback == null) {
            callback = NettyCallbackCollection.INSTANCE.getNettyCallback(NettyClient.class);
        }

        if (!future.isSuccess()) {
            sender.setChannel(null);
            callback.onConnectFailed();
            callback.onStartCountdown(Constants.NETTY_SERVER_CONNECTION_ERROR_SLEEP_TIME);

            final EventLoop loop = channelFuture.channel().eventLoop();
            loop.schedule(() -> {
                try {
                    callback.onConnecting();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                try {
                    run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, Constants.NETTY_SERVER_CONNECTION_ERROR_SLEEP_TIME, TimeUnit.MILLISECONDS);
        } else {
            sender.setChannel(channelFuture.channel());
            synchronized (sender.channelLock) {
                sender.channelLock.notifyAll();
            }
            callback.onConnectSuccess();
        }
    };

    public void run() throws InterruptedException {
        if (!needReconnect) {
            return;
        }
        if (serverInfo == null) {
            return;
        }

        String ip = serverInfo.getIp();
        int port = GlobalConfiguration.NETTY_SERVER_PORT;

        if (StringUtils.isBlank(ip) ||
                !NetworkUtils.isValidPort(port)) {
            return;
        }
        if (group.isTerminated()) {
            group = new NioEventLoopGroup();
            bootstrap.group(group);
        }

        channelFuture = bootstrap.connect(ip, port);
        channelFuture.addListener(futureListener);
        channelFuture.channel().closeFuture().sync();
    }

    public class PacketSender {
        @Setter(AccessLevel.PRIVATE)
        private Channel channel;
        private final Object channelLock = new Object();
        @Getter
        private final ReentrantLock aesKeyLock = new ReentrantLock(true);

        private boolean waitChannel(long timeoutMillis) {
            if (channel == null) {
                synchronized (channelLock) {
                    try {
                        channelLock.wait(timeoutMillis);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if (channel == null) {
                    log.error(AppLocale.PACKET_SENDER_TIMEOUT);
                    return false;
                }
            }
            return true;
        }

        private boolean waitAesKey() {
            if (GlobalConfiguration.aesKey == null) {
                try {
                    aesKeyLock.lock();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (GlobalConfiguration.aesKey == null) {
                    log.error(AppLocale.PACKET_SENDER_TIMEOUT);
                    return false;
                }
            }
            return true;
        }

        public void send(
                GeneratedMessageV3 generatedMessageV3
        ) throws ExecutionException, InterruptedException, TimeoutException {
            doSend(generatedMessageV3, null, false, null, null);
        }

        public void send(
                GeneratedMessageV3 generatedMessageV3,
                String requestId
        ) throws ExecutionException, InterruptedException, TimeoutException {
            doSend(generatedMessageV3, requestId, false, null, null);
        }

        public void send(
                GeneratedMessageV3 generatedMessageV3,
                String requestId,
                ResultEnum resultEnum
        ) throws ExecutionException, InterruptedException, TimeoutException {
            doSend(generatedMessageV3, requestId, false, resultEnum, null);
        }

        public Pair<BaseProto.Packet, Message> sendSynchronously(
                GeneratedMessageV3 generatedMessageV3
        ) throws ExecutionException, InterruptedException, TimeoutException {
            return sendSynchronously(generatedMessageV3, null);
        }

        public Pair<BaseProto.Packet, Message> sendSynchronously(
                GeneratedMessageV3 generatedMessageV3,
                ChannelFutureListener channelFutureListener
        ) throws ExecutionException, InterruptedException, TimeoutException {
            return doSend(generatedMessageV3, null, true, null, channelFutureListener);
        }

        private Pair<BaseProto.Packet, Message> doSend(
                GeneratedMessageV3 generatedMessageV3,
                String requestId,
                boolean sync,
                ResultEnum resultEnum,
                ChannelFutureListener channelFutureListener
        ) throws ExecutionException, InterruptedException, TimeoutException {
            if (!waitChannel(0)) {
                return null;
            };
            if (generatedMessageV3.getClass() != SecurityProto.ExchangeRsaPublicKeyPacket.class &&
                    !waitAesKey()) {
                return null;
            }
            if (!sync) {
                ChannelFuture channelFuture = Writer.write(channel, generatedMessageV3, requestId, resultEnum);
                if (channelFutureListener != null) {
                    channelFuture.addListener(channelFutureListener);
                }
                return null;
            }
            SyncFuture<Pair<BaseProto.Packet, Message>> syncFuture = new SyncFuture<>();

            return Writer.writeSynchronously(channel, generatedMessageV3, syncFuture, requestId, resultEnum, channelFutureListener);
        }
    }
}

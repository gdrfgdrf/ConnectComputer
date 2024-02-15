package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Initializer;

import java.util.concurrent.TimeUnit;

import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Handler.*;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.Protobuf.BaseProto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * @author gdrfgdrf
 */
public class ClientInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(handler());
    }

    public ChannelHandler[] handler() {
        return new ChannelHandler[]{
                new ProtobufVarint32FrameDecoder(),
                new ProtobufDecoder(BaseProto.Packet.getDefaultInstance()),
                new ProtobufVarint32LengthFieldPrepender(),
                new ProtobufEncoder(),
                new DecryptHandler(),
                new ConnectionDecryptHandler(),
                new EncryptHandler(),
                new ClientVersionHandler(),
                new ConnectionEncryptHandler(),
                new IdleStateHandler(6, 0, 0, TimeUnit.SECONDS),
                new HeartPingPongHandler(),
                new DistributeHandler(),
        };
    }
}

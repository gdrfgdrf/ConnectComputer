package cn.gdrfgdrf.ConnectComputerServer.Netty.Initializer;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Handler.*;
import cn.gdrfgdrf.Protobuf.BaseProto;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * @author gdrfgdrf
 */
public class ServerInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel socketChannel) {
        socketChannel.pipeline().addLast(handler());
    }

    public ChannelHandler[] handler() {
        return new ChannelHandler[]{
                new ProtobufVarint32FrameDecoder(),
                new ProtobufDecoder(BaseProto.Packet.getDefaultInstance()),
                new ProtobufVarint32LengthFieldPrepender(),
                new ProtobufEncoder(),
                new DecryptHandler(),
                new EncryptHandler(),
                new IdleStateHandler(6, 5, 0, TimeUnit.SECONDS),
                new HeartPingPongHandler(),
                new ClientVersionHandler(),
                new AuthenticateHandler(),
                new DistributeHandler()
        };
    }
}

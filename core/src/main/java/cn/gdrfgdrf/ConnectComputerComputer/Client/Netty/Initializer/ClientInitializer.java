/*
 * Copyright (C) 2024 Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

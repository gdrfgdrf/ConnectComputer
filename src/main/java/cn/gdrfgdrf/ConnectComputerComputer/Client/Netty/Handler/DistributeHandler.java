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

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Handler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Exception.NettyNotFoundPacketHandlerException;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.ConnectionStateChangePoster.ConnectionStateChangePoster;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.PacketPoster.PacketPoster;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.SyncPacketPoster.SyncPacketPoster;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.SyncResponsePacketPoster.SyncResponsePacketPoster;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Utils.MessageTypeUnpacker;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.NetworkUtils;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author gdrfgdrf
 */
public class DistributeHandler extends SimpleChannelInboundHandler<BaseProto.Packet> {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ConnectionStateChangePoster.INSTANCE.post(true);
        super.channelActive(ctx);
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        ConnectionStateChangePoster.INSTANCE.post(false);

        GlobalConfiguration.aesKey = null;
        try {
            NetworkUtils.closeNettyThoroughly(ctx.channel());
            NettyClient.INSTANCE.run();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, BaseProto.Packet packet) throws Exception {
        Message message = MessageTypeUnpacker.unpack(packet.getData());
        BasePacketHandler basePacketHandler = NettyClient.INSTANCE.getHandlerMap().get(message.getClass());
        if (basePacketHandler == null) {
            throw new NettyNotFoundPacketHandlerException(message);
        }
        basePacketHandler.handle(ctx.channel(), packet, message);

        SyncPacketPoster.INSTANCE.notifySynchronizedPacketReceived(packet, message);
        SyncResponsePacketPoster.INSTANCE.notifySynchronizedResponsePacketReceived(packet, message);
        try {
            PacketPoster.INSTANCE.post(packet, message);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        Thread.getDefaultUncaughtExceptionHandler().uncaughtException(Thread.currentThread(), cause);
    }
}

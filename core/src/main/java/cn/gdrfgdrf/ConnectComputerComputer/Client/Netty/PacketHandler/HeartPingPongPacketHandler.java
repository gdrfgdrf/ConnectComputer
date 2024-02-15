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

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Connection.Heart.HeartProto;
import com.google.protobuf.Message;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
@PacketHandler(support = {
        HeartProto.HeartPingPacket.class
})
public class HeartPingPongPacketHandler implements BasePacketHandler {
    @Override
    public void handle(
            Channel channel,
            BaseProto.Packet packet,
            Message message
    ) throws Exception {
        HeartProto.HeartPongPacket heartPongPacket = HeartProto.HeartPongPacket.newBuilder()
                .build();
        NettyClient.INSTANCE.getSender().send(heartPongPacket);
    }
}

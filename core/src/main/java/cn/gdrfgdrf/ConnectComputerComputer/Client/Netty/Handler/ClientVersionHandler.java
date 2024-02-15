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

import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.Protobuf.BaseProto;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author gdrfgdrf
 */
public class ClientVersionHandler extends MessageToMessageEncoder<BaseProto.Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, BaseProto.Packet packet, List<Object> out) throws Exception {
        out.add(
                packet.toBuilder()
                        .setClientVersion(Constants.VERSION)
                        .build()
        );
    }
}

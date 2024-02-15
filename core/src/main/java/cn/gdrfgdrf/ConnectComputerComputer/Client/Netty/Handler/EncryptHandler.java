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

import cn.gdrfgdrf.ConnectComputerComputer.Common.Key.AesKey;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AESUtils;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.AnyPacketProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityEnumProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityProto;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Utils.AnyPacketPacker;
import com.google.protobuf.ByteString;

import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author gdrfgdrf
 */
public class EncryptHandler extends MessageToMessageEncoder<BaseProto.Packet> {
    @Override
    protected void encode(
            ChannelHandlerContext ctx,
            BaseProto.Packet msg,
            List<Object> out
    ) throws Exception {
        if (GlobalConfiguration.aesKey == null) {
            out.add(msg);
            return;
        }
        AesKey aesKey = GlobalConfiguration.aesKey;
        byte[] bytes = msg.toByteArray();
        bytes = AESUtils.encryptToByteByCBC(bytes, aesKey.getIv(), aesKey.getKey());

        SecurityProto.EncryptBlockPacket encryptBlockPacket = SecurityProto.EncryptBlockPacket.newBuilder()
                .setEncryptAlgorithm(SecurityEnumProto.EncryptAlgorithmEnum.AES)
                .setEncrypted(ByteString.copyFrom(bytes))
                .build();
        AnyPacketProto.AnyPacket anyPacket = AnyPacketPacker.pack(encryptBlockPacket);

        BaseProto.Packet result = BaseProto.Packet.newBuilder()
                .setData(anyPacket)
                .build();

        out.add(result);
    }
}

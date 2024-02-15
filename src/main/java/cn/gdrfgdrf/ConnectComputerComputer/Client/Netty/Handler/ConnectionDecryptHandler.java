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

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Utils.MessageTypeUnpacker;
import cn.gdrfgdrf.ConnectComputerComputer.Common.Key.AesKey;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AESUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils;
import cn.gdrfgdrf.Protobuf.Action.Computer.Security.ComputerSecurityProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.Security.ControllerSecurityProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityEnumProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.security.PublicKey;
import java.util.List;

/**
 * @author gdrfgdrf
 */
public class ConnectionDecryptHandler extends MessageToMessageDecoder<BaseProto.Packet> {
    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, BaseProto.Packet packet, List<Object> out) throws Exception {
        Message message = MessageTypeUnpacker.unpack(packet.getData());
        if (!(message instanceof ControllerSecurityProto.EncryptBlockPacket) &&
                !(message instanceof ComputerSecurityProto.EncryptBlockPacket)) {
            out.add(packet);
            return;
        }
        byte[] bytes = getEncrypted(message).toByteArray();
        SecurityEnumProto.EncryptAlgorithmEnum encryptAlgorithmEnum = getEncryptAlgorithm(message);

        if (encryptAlgorithmEnum == SecurityEnumProto.EncryptAlgorithmEnum.RSA) {
            if (GlobalConfiguration.tempOwnPrivateKey == null) {
                return;
            }
            bytes = RSAUtils.privateDecryptToByte(
                    bytes,
                    GlobalConfiguration.tempOwnPrivateKey
            );
        }
        if (encryptAlgorithmEnum == SecurityEnumProto.EncryptAlgorithmEnum.AES) {
            if (GlobalConfiguration.tempAesKey == null) {
                return;
            }
            AesKey aesKey = GlobalConfiguration.tempAesKey;
            bytes = AESUtils.decryptToByteByCBC(bytes, aesKey.getIv(), aesKey.getKey());
        }

        BaseProto.Packet result = BaseProto.Packet.newBuilder()
                .mergeFrom(bytes)
                .setRequestId(packet.getRequestId())
                .build();

        out.add(result);
    }

    private SecurityEnumProto.EncryptAlgorithmEnum getEncryptAlgorithm(Message message) {
        if (message instanceof ControllerSecurityProto.EncryptBlockPacket encryptBlockPacket) {
            return encryptBlockPacket.getEncryptAlgorithm();
        }
        if (message instanceof ComputerSecurityProto.EncryptBlockPacket encryptBlockPacket) {
            return encryptBlockPacket.getEncryptAlgorithm();
        }
        return null;
    }

    private ByteString getEncrypted(Message message) {
        if (message instanceof ControllerSecurityProto.EncryptBlockPacket encryptBlockPacket) {
            return encryptBlockPacket.getEncrypted();
        }
        if (message instanceof ComputerSecurityProto.EncryptBlockPacket encryptBlockPacket) {
            return encryptBlockPacket.getEncrypted();
        }
        return null;
    }
}

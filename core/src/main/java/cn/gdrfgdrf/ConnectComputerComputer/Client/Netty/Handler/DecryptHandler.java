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
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AESUtils;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityEnumProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityProto;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Utils.MessageTypeUnpacker;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.RSA;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author gdrfgdrf
 */
public class DecryptHandler extends MessageToMessageDecoder<BaseProto.Packet> {
    private final RSA rsa;

    {
        DataStore dataStore = BeanManager.getInstance().getBean("DataStore");
        rsa = dataStore.getRsa();
    }

    @Override
    protected void decode(
            ChannelHandlerContext ctx,
            BaseProto.Packet msg,
            List<Object> out
    ) throws Exception {
        Message message = MessageTypeUnpacker.unpack(msg.getData());

        if (message instanceof SecurityProto.EncryptBlockPacket encryptBlockPacket) {
            byte[] bytes = encryptBlockPacket.getEncrypted().toByteArray();

            if (encryptBlockPacket.getEncryptAlgorithm() == SecurityEnumProto.EncryptAlgorithmEnum.RSA) {
                bytes = RSAUtils.privateDecryptToByte(
                        bytes,
                        rsa.getNettyPrivateKey()
                );
            }
            if (encryptBlockPacket.getEncryptAlgorithm() == SecurityEnumProto.EncryptAlgorithmEnum.AES) {
                if (GlobalConfiguration.aesKey == null) {
                    return;
                }
                AesKey aesKey = GlobalConfiguration.aesKey;
                bytes = AESUtils.decryptToByteByCBC(bytes, aesKey.getIv(), aesKey.getKey());
            }

            BaseProto.Packet result = BaseProto.Packet.newBuilder()
                    .mergeFrom(bytes)
                    .build();

            out.add(result);
        } else {
            out.add(msg);
        }
    }
}

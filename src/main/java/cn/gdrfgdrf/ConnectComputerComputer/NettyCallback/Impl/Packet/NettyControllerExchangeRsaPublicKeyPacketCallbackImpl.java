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

package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyControllerExchangeRsaPublicKeyPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils;
import cn.gdrfgdrf.Protobuf.Action.Computer.Security.ComputerSecurityProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.Security.ControllerSecurityProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityEnumProto;
import com.google.protobuf.ByteString;
import org.apache.tomcat.util.codec.binary.Base64;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author gdrfgdrf
 */
public class NettyControllerExchangeRsaPublicKeyPacketCallbackImpl implements NettyControllerExchangeRsaPublicKeyPacketCallback {
    @Override
    public void onExchangeRsaPublicKeyPacket(PacketMessage packetMessage) throws Exception {
        packetMessage.log();

        ControllerSecurityProto.ExchangeRsaPublicKeyPacket message = packetMessage.getMessage();
        PublicKey controllerPublicKey = RSAUtils.getPublicKey(message.getPublicKey());

        KeyPair keyPair = RSAUtils.generateRSAKeyPair();
        PrivateKey privateKey = keyPair.getPrivate();
        PublicKey publicKey = keyPair.getPublic();

        String encryptedPublicKey = RSAUtils.publicEncrypt(
                Base64.encodeBase64String(publicKey.getEncoded()),
                controllerPublicKey
        ).toString();

        ComputerSecurityProto.ExchangeRsaPublicKeyPacket exchangeRsaPublicKeyPacket =
                ComputerSecurityProto.ExchangeRsaPublicKeyPacket.newBuilder()
                        .setPublicKey(encryptedPublicKey)
                        .build();
        NettyClient.INSTANCE.getSender().send(exchangeRsaPublicKeyPacket, packetMessage.getPacket().getRequestId());

        GlobalConfiguration.tempPublicKey = controllerPublicKey;
        GlobalConfiguration.tempOwnPrivateKey = privateKey;
        GlobalConfiguration.tempOwnPublicKey = publicKey;
    }
}

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

import cn.gdrfgdrf.ConnectComputerComputer.Common.Key.AesKey;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyInitAesKeyPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AESUtils;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityProto;
import com.google.protobuf.Message;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
@PacketHandler(support = SecurityProto.InitAesKeyPacket.class)
public class InitAesKeyPacketHandler implements BasePacketHandler {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, Message message) throws Exception {
        SecurityProto.InitAesKeyPacket initAesKeyPacket = (SecurityProto.InitAesKeyPacket) message;
        GlobalConfiguration.aesKey = new AesKey(
                AESUtils.getIv(initAesKeyPacket.getIv()),
                AESUtils.getKey(initAesKeyPacket.getKey())
        );
        if (NettyClient.INSTANCE.getSender().getAesKeyLock().isLocked()) {
            try {
                NettyClient.INSTANCE.getSender().getAesKeyLock().unlock();
            } catch (Exception ignored) {
            }
        }

        NettyInitAesKeyPacketCallback callback = NettyCallbackCollection.INSTANCE.getNettyCallback(InitAesKeyPacketHandler.class);
        callback.onInitAesKeyPacket(PacketMessage.createByMessage(packet, message));
    }
}

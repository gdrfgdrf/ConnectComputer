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
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandler;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerErrorProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerSuccessProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.ComputerData;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyDefaultPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import com.google.protobuf.Message;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
@PacketHandler(support = {
        ComputerSuccessProto.RegisterComputerSuccessPacket.class,
        ComputerErrorProto.AlreadyRegisterComputerPacket.class
})
public class RegisterComputerPacketHandler implements BasePacketHandler {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, Message message) throws Exception {
        NettyDefaultPacketCallback callback = NettyCallbackCollection.INSTANCE.getNettyCallback(Object.class);
        PacketMessage packetMessage = PacketMessage.createByMessage(packet, message);

        if (message instanceof ComputerSuccessProto.RegisterComputerSuccessPacket) {
            DataStore dataStore = BeanManager.getInstance().getBean("DataStore");
            ComputerData computerData = dataStore.getComputerData();
            ComputerSuccessProto.RegisterComputerSuccessPacket computerSuccessPacket =
                    packetMessage.getMessage();

            computerData.setId(computerSuccessPacket.getId());
            dataStore.saveDataBean(computerData);
        }

        callback.onPacket(packetMessage);
    }
}

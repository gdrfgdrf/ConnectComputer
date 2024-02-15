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

package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message;

import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import com.google.protobuf.Message;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Data
@Slf4j
public class PacketMessage {
    private BaseProto.Packet packet;
    private Message message;

    public void log() {
        if (packet.getCode() == Constants.SUCCESS) {
            log.info(packet.getMessage());
            return;
        }
        log.error(packet.getMessage());
    }

    public static PacketMessage createByMessage(BaseProto.Packet packet, Message message) {
        PacketMessage packetMessage = new PacketMessage();
        packetMessage.setPacket(packet);
        packetMessage.setMessage(message);
        return packetMessage;
    }

    public <T> T getMessage() {
        return (T) message;
    }
}

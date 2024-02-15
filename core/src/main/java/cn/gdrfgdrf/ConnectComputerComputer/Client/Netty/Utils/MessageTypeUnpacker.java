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

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Utils;

import cn.gdrfgdrf.Protobuf.Common.AnyPacketProto;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

/**
 * @author gdrfgdrf
 */
public class MessageTypeUnpacker {

    public static Message unpack(AnyPacketProto.AnyPacket anyPacket)
            throws InvalidProtocolBufferException {
        MessageTypeLookup typeLookup =
                MessageTypeLookupUtils.getMessageTypeLookup();
        return unpack(typeLookup, anyPacket);
    }

    public static Message unpack(MessageTypeLookup typeLookup, AnyPacketProto.AnyPacket anyPacket)
            throws InvalidProtocolBufferException {
        Class<? extends Message> messageClass = typeLookup.lookup(anyPacket.getTypeUrl());
        AnyPacketPacker anyPacketPacker = new AnyPacketPacker(anyPacket);
        return anyPacketPacker.unpack(messageClass);
    }
}

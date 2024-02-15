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
import com.google.protobuf.Descriptors;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;

/**
 * @author gdrfgdrf
 */
public class AnyPacketPacker {
    private static final String PACKAGE_NAME = "cn.gdrfgdrf.Protobuf";

    private final AnyPacketProto.AnyPacket anyPacket;
    private volatile Message cachedUnpackValue;
    public AnyPacketPacker(AnyPacketProto.AnyPacket anyPacket) {
        this.anyPacket = anyPacket;
    }

    public static <T extends Message> AnyPacketProto.AnyPacket pack(T message) {
        final String typeUrl = getTypeUrl(message.getDescriptorForType());

        return AnyPacketProto.AnyPacket.newBuilder()
                .setTypeUrl(typeUrl)
                .setValue(message.toByteString())
                .build();
    }

    public static <T extends Message> AnyPacketProto.AnyPacket pack(T message, String typeUrlPrefix) {
        String typeUrl = getTypeUrl(typeUrlPrefix, message.getDescriptorForType());

        return AnyPacketProto.AnyPacket.newBuilder()
                .setTypeUrl(typeUrl)
                .setValue(message.toByteString())
                .build();
    }

    public <T extends Message> T unpack(Class<T> clazz) throws InvalidProtocolBufferException {
        if (!is(clazz)) {
            throw new InvalidProtocolBufferException("Type of the Any message does not match the given class.");
        }
        if (cachedUnpackValue != null) {
            return (T) cachedUnpackValue;
        }
        T defaultInstance = Internal.getDefaultInstance(clazz);
        T result = (T) defaultInstance.getParserForType().parseFrom(anyPacket.getValue());
        cachedUnpackValue = result;
        return result;
    }

    public <T extends Message> boolean is(Class<T> clazz) {
        T defaultInstance = Internal.getDefaultInstance(clazz);
        return getTypeNameFromTypeUrl(anyPacket.getTypeUrl()).equals(
                defaultInstance.getDescriptorForType().getFullName());
    }

    private static String getTypeUrl(final Descriptors.Descriptor descriptor) {
        return getTypeUrl(PACKAGE_NAME, descriptor);
    }

    private static String getTypeNameFromTypeUrl(String typeUrl) {
        int pos = typeUrl.lastIndexOf('/');
        return pos == -1 ? "" : typeUrl.substring(pos + 1);
    }

    private static String getTypeUrl(String typeUrlPrefix, Descriptors.Descriptor descriptor) {
        return typeUrlPrefix.endsWith("/")
                ? typeUrlPrefix + descriptor.getFullName()
                : typeUrlPrefix + "/" + descriptor.getFullName();
    }
}
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

import com.google.protobuf.Descriptors;
import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
public class MessageTypeLookup {
    private final Map<String, Class<? extends Message>> TYPE_MESSAGE_CLASS_MAP;

    private MessageTypeLookup(Map<String, Class<? extends Message>> typeMessageClassMap) {
        this.TYPE_MESSAGE_CLASS_MAP = typeMessageClassMap;
    }

    public Class<? extends Message> lookup(final String typeUrl) {
        String type = typeUrl;
        if(type.contains("/")) {
            type = getTypeUrlSuffix(type);
        }
        return TYPE_MESSAGE_CLASS_MAP.get(type);
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    private static String getTypeUrlSuffix(String fullTypeUrl) {
        String[] parts = fullTypeUrl.split("/");
        return parts[parts.length - 1];
    }

    public static class Builder {
        private final Map<String, Class<? extends Message>> TYPE_MESSAGE_CLASS_BUILDER_MAP;

        public Builder() {
            TYPE_MESSAGE_CLASS_BUILDER_MAP = new HashMap<>();
        }

        public Builder addMessageTypeMapping(
                final Descriptors.Descriptor descriptor,
                final Class<? extends Message> messageClass
        ) {
            TYPE_MESSAGE_CLASS_BUILDER_MAP.put(descriptor.getFullName(), messageClass);
            return this;
        }

        public MessageTypeLookup build() {
            return new MessageTypeLookup(TYPE_MESSAGE_CLASS_BUILDER_MAP);
        }
    }
}

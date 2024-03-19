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

import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * @author gdrfgdrf
 */
public class MessageTypeLookupUtils {
    public static MessageTypeLookup getMessageTypeLookup() {
        Set<Class<? extends GeneratedMessage>> classes = ClassScanner.lookupClasses();
        return generateMessageTypeLookup(classes);
    }

    private static MessageTypeLookup generateMessageTypeLookup(Set<Class<? extends GeneratedMessage>> classes) {
        MessageTypeLookup.Builder messageTypeLookupBuilder = MessageTypeLookup.newBuilder();
        try {
            for (Class<? extends GeneratedMessage> clazz : classes) {
                Message.Builder builder = (Message.Builder) clazz.getMethod("newBuilder").invoke(null);
                Message message = builder.build();
                messageTypeLookupBuilder.addMessageTypeMapping(message.getDescriptorForType(), clazz);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return messageTypeLookupBuilder.build();
    }
}
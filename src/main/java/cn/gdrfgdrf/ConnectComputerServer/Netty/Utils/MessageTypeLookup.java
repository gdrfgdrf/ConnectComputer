package cn.gdrfgdrf.ConnectComputerServer.Netty.Utils;

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

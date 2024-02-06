package cn.gdrfgdrf.ConnectComputerServer.Netty.Utils;

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

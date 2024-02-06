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

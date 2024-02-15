package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.PacketPoster;

import cn.gdrfgdrf.Protobuf.BaseProto;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author gdrfgdrf
 */

public enum PacketPoster {
    INSTANCE;

    private final Map<Class<? extends Message>, List<PacketReceiver>> RECEIVER_MAP = new HashMap<>();

    public void registerPacketReceiver(PacketReceiver packetReceiver) {
        Class<? extends GeneratedMessageV3>[] types = packetReceiver.type();
        for (Class<? extends GeneratedMessageV3> clazz : types) {
            RECEIVER_MAP.computeIfAbsent(clazz, k -> new LinkedList<>());
            List<PacketReceiver> packetReceivers = RECEIVER_MAP.get(clazz);
            packetReceivers.add(packetReceiver);
        }
    }

    public void unregisterPacketReceiver(PacketReceiver packetReceiver) {
        Class<? extends GeneratedMessageV3>[] types = packetReceiver.type();
        for (Class<? extends GeneratedMessageV3> clazz : types) {
            if (!RECEIVER_MAP.containsKey(clazz)) {
                continue;
            }
            List<PacketReceiver> packetReceivers = RECEIVER_MAP.get(clazz);
            packetReceivers.remove(packetReceiver);
        }
    }

    public void post(BaseProto.Packet basePacket, Message message) {
        if (!RECEIVER_MAP.containsKey(message.getClass())) {
            throw new NullPointerException("Not found packet receiver by " + message.getClass().getSimpleName());
        }
        List<PacketReceiver> packetReceivers = RECEIVER_MAP.get(message.getClass());
        for (PacketReceiver packetReceiver : packetReceivers) {
            try {
                packetReceiver.onReceived(basePacket, message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.SyncResponsePacketPoster;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.Synchronization.SyncFuture;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

/**
 * @author gdrfgdrf
 */
public enum SyncResponsePacketPoster {
    INSTANCE;

    private final Map<String, SyncFuture<Pair<BaseProto.Packet, Message>>> MAP = new ConcurrentHashMap<>();

    public void registerSynchronizedPacket(BaseProto.Packet packet, SyncFuture<Pair<BaseProto.Packet, Message>> syncFuture) {
        MAP.put(packet.getRequestId(), syncFuture);
    }

    public void notifySynchronizedResponsePacketReceived(BaseProto.Packet packet, Message message) throws InvalidProtocolBufferException {
        SyncFuture<Pair<BaseProto.Packet, Message>> syncFuture = MAP.get(packet.getRequestId());
        if (syncFuture != null) {
            Pair<BaseProto.Packet, Message> pair = new ImmutablePair<>(packet, message);
            syncFuture.setResult(pair);
            MAP.remove(packet.getRequestId());
        }
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.SyncPacketPoster;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.Synchronization.SyncFuture;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.Protobuf.BaseProto;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author gdrfgdrf
 */
public enum SyncPacketPoster {
    INSTANCE;

    private final Map<Class<? extends Message>, List<SyncFuture<Pair<BaseProto.Packet, Message>>>> MAP = new ConcurrentHashMap<>();

    public Pair<BaseProto.Packet, Message> waitPacket(Class<? extends GeneratedMessageV3> type)
            throws ExecutionException, InterruptedException {
        SyncFuture<Pair<BaseProto.Packet, Message>> syncFuture = new SyncFuture<>();
        MAP.computeIfAbsent(type, k -> new CopyOnWriteArrayList<>());
        MAP.get(type).add(syncFuture);
        return syncFuture.get();
    }

    public void notifySynchronizedPacketReceived(BaseProto.Packet packet, Message message) {
        if (!MAP.containsKey(message.getClass())) {
            return;
        }
        List<SyncFuture<Pair<BaseProto.Packet, Message>>> syncFutures = MAP.get(message.getClass());
        Pair<BaseProto.Packet, Message> pair = new ImmutablePair<>(packet, message);

        syncFutures.forEach(syncFuture -> {
            syncFuture.setResult(pair);
        });
        MAP.remove(message.getClass());
    }
}

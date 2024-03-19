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

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.SyncPacketPoster;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.Synchronization.SyncFuture;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import com.google.protobuf.GeneratedMessage;
import com.google.protobuf.Message;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

    public Pair<BaseProto.Packet, Message> waitPacket(Class<? extends GeneratedMessage> type)
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

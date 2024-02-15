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

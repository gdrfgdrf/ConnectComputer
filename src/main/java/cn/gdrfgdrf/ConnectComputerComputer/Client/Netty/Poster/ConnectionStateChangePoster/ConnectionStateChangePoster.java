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

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.ConnectionStateChangePoster;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
public enum ConnectionStateChangePoster {
    INSTANCE;

    private final List<ConnectionStateChangeReceiver> RECEIVERS = new LinkedList<>();

    public void registerConnectionStateChangeReceiver(ConnectionStateChangeReceiver connectionStateChangeReceiver) {
        RECEIVERS.add(connectionStateChangeReceiver);
    }

    public void unregisterConnectionStateChangeReceiver(ConnectionStateChangeReceiver connectionStateChangeReceiver) {
        RECEIVERS.remove(connectionStateChangeReceiver);
    }

    public void post(boolean connected) {
        RECEIVERS.forEach(connectionStateChangeReceiver -> {
            try {
                if (connected) {
                    connectionStateChangeReceiver.onConnected();
                } else {
                    connectionStateChangeReceiver.onDisconnected();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

}

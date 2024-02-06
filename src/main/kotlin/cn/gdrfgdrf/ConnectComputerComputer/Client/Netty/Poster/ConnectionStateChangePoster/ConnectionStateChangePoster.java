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

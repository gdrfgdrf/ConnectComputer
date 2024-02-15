package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.ConnectionStateChangePoster;

/**
 * @author gdrfgdrf
 */
public interface ConnectionStateChangeReceiver {
    void onConnected();
    void onDisconnected();
}

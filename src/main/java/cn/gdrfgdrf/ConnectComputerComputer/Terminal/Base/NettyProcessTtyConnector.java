package cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import com.jediterm.terminal.TtyConnector;

import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author gdrfgdrf
 */
public abstract class NettyProcessTtyConnector implements TtyConnector {
    protected boolean connected = false;
    private final Object waitForLock = new Object();

    @Override
    public void write(byte[] bytes) throws IOException {

    }

    @Override
    public void write(String s) throws IOException {

    }

    @Override
    public int waitFor() throws InterruptedException {
        synchronized (waitForLock) {
            waitForLock.wait();
        }
        return 0;
    }

    @Override
    public boolean ready() throws IOException {
        return connected;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public void close() {
        connected = false;
        synchronized (waitForLock) {
            waitForLock.notifyAll();
        }
    }

    @Override
    public String getName() {
        return "Local";
    }
}

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

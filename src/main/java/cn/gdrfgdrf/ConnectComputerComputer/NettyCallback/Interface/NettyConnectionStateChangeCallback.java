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

package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackInfo;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.NettyConnectionStateChangeCallbackImpl;

/**
 * @author gdrfgdrf
 */
@CallbackInfo(
        targetClass = NettyClient.class,
        implementedClass = NettyConnectionStateChangeCallbackImpl.class
)
public interface NettyConnectionStateChangeCallback extends NettyCallback {
    void onStartCountdown(long milliseconds) throws Exception;
    void onConnecting() throws Exception;
    void onConnectSuccess() throws Exception;
    void onConnectFailed() throws Exception;
}

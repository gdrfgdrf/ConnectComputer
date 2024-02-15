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

package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Manager;


import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackClass;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.*;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.*;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;

/**
 * @author gdrfgdrf
 */
@CallbackClass(
        classes = {
                NettyDefaultPacketCallback.class,

                NettyConnectionStateChangeCallback.class,

                NettyInitAesKeyPacketCallback.class,
                NettyLoginPacketCallback.class,
                NettyGoodbyePacketCallback.class,

                NettyControllerExchangeRsaPublicKeyPacketCallback.class,
                NettyControllerInitAesKeyPacketCallback.class,

                NettyComputerIsControlledPacketCallback.class,
                NettyComputerDisconnectedPacketCallback.class,
                NettyControllerDisconnectedPacketCallback.class,
                NettyTerminalResponsePacketCallback.class,
                NettyKeyEventPacketCallback.class,
                NettyControllerTerminalClosedPacketCallback.class,
                NettyComputerTerminalClosedPacketCallback.class
        }
)
public class NettyCallbackManager extends AbstractNettyCallbackManager implements Bean {
    @Override
    public void init() throws Exception {
        super.initNettyCallback();
    }
}

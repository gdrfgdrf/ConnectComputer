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

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.*;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandlerClass;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import cn.gdrfgdrf.Protobuf.Action.Computer.Security.ComputerSecurityProto;

/**
 * @author gdrfgdrf
 */
@PacketHandlerClass(
        clazz = {
                GoodbyePacketHandler.class,
                HeartPingPongPacketHandler.class,

                InitAesKeyPacketHandler.class,
                LoginPacketHandler.class,
                RegisterComputerPacketHandler.class,
                ComputerStatusChangePacketHandler.class,

                ControllerExchangeRsaPublicKeyPacketHandler.class,
                ControllerInitAesKeyPacketHandler.class,

                ComputerIsControlledPacketHandler.class,
                ControllerDisconnectedPacketHandler.class,
                ComputerDisconnectedPacketHandler.class,
                KeyEventPacketHandler.class,
                TerminalResponsePacketHandler.class,
                ControllerTerminalClosedPacketHandler.class,
                ComputerTerminalClosedPacketHandler.class,

                DefaultPacketHandler.class
        }
)
public class PacketHandlerManager extends AbstractPacketHandlerManager implements Bean {
    @Override
    public void init() throws Exception {
        super.initHandler();
    }
}

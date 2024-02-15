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

package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyTerminalResponsePacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.RemoteTerminal;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author gdrfgdrf
 */
public class NettyTerminalResponsePacketCallbackImpl implements NettyTerminalResponsePacketCallback {
    @Override
    public void onTerminalResponsePacket(PacketMessage packetMessage) throws Exception {
        if (GlobalConfiguration.terminal == null) {
            return;
        }

        ComputerProto.TerminalResponsePacket message = packetMessage.getMessage();
        String response = message.getResponse();

        GlobalConfiguration.terminal.userInput(response);
    }
}

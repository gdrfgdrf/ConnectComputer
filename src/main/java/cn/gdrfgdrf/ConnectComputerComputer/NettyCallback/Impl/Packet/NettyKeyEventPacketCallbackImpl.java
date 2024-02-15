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
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyKeyEventPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import com.jediterm.terminal.ui.JediTermWidget;
import org.apache.commons.lang3.CharUtils;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;

/**
 * @author gdrfgdrf
 */
public class NettyKeyEventPacketCallbackImpl implements NettyKeyEventPacketCallback {
    @Override
    public void onKeyEventPacket(PacketMessage packetMessage) throws Exception {
        if (GlobalConfiguration.terminal == null) {
            return;
        }

        ControllerProto.KeyEventPacket message = packetMessage.getMessage();
        JediTermWidget widget = GlobalConfiguration.terminal.getJediTermWidget();
        KeyEvent keyEvent = new KeyEvent(
                widget.getComponent(),
                message.getId(),
                message.getWhen(),
                message.getModifiers(),
                message.getKeyCode(),
                CharUtils.toChar(message.getKeyChar()),
                message.getKeyLocation()
        );

        widget.getTerminalPanel().processKeyEvent(keyEvent);
    }
}

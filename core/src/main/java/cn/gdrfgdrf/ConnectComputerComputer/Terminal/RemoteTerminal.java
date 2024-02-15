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

package cn.gdrfgdrf.ConnectComputerComputer.Terminal;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Connector.RealtimeCallbackNettyProcessTtyConnector;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import com.jediterm.terminal.TtyConnector;
import org.jetbrains.annotations.NotNull;

/**
 * @author gdrfgdrf
 */
public class RemoteTerminal extends Terminal {

    public RemoteTerminal() {
        super(true);
    }

    @Override
    public void close() {
        closeByOpposite = false;
        super.close();

        ControllerProto.TerminalClosedPacket terminalClosedPacket = ControllerProto.TerminalClosedPacket.newBuilder()
                .build();
        try {
            NettyClient.INSTANCE.getSender().send(terminalClosedPacket);
        } catch (Exception ignored) {
        }
    }

    @Override
    public void closeByOpposite() {
        closeByOpposite = true;
        super.close();
    }

    @Override
    protected @NotNull TtyConnector createTtyConnector() {
        return new RealtimeCallbackNettyProcessTtyConnector(null);
    }
}

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

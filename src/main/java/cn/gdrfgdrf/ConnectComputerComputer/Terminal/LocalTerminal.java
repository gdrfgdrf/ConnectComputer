package cn.gdrfgdrf.ConnectComputerComputer.Terminal;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Callback.TerminalCharArrayResponseCallback;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Connector.RealtimeCallbackPtyProcessTtyConnector;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.Platform;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import com.jediterm.terminal.TtyConnector;
import com.pty4j.PtyProcess;
import com.pty4j.PtyProcessBuilder;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class LocalTerminal extends Terminal {
    private final TerminalCharArrayResponseCallback callback;

    public LocalTerminal(TerminalCharArrayResponseCallback callback) {
        super(false);
        this.callback = callback;
    }

    @Override
    public void close() {
        closeByOpposite = false;
        super.close();

        ComputerProto.TerminalClosedPacket terminalClosedPacket = ComputerProto.TerminalClosedPacket.newBuilder()
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

    //Copy from com.jediterm.example.BasicTerminalShellExample#createTtyConnector of JediTerm.
    @Override
    protected TtyConnector createTtyConnector() {
        try {
            Map<String, String> envs = System.getenv();
            String[] command;
            if (Platform.isWindows()) {
                command = new String[]{"cmd.exe"};
            } else {
                command = new String[]{"/bin/bash", "--login"};
                envs = new HashMap<>(System.getenv());
                envs.put("TERM", "xterm-256color");
            }

            PtyProcess process = new PtyProcessBuilder()
                    .setCommand(command)
                    .setEnvironment(envs)
                    .start();
            return new RealtimeCallbackPtyProcessTtyConnector(process, StandardCharsets.UTF_8, callback);
        } catch (Exception e) {
            throw new IllegalStateException(e);
        }
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyComputerTerminalClosedPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;

/**
 * @author gdrfgdrf
 */
public class NettyComputerTerminalClosedPacketCallbackImpl implements NettyComputerTerminalClosedPacketCallback {
    @Override
    public void onTerminalClosedPacket(PacketMessage packetMessage) throws Exception {
        packetMessage.log();

        if (GlobalConfiguration.terminal != null) {
            Terminal terminal = GlobalConfiguration.terminal;
            terminal.closeByOpposite();
        }
    }
}

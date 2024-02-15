package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyControllerTerminalClosedPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;

/**
 * @author gdrfgdrf
 */
public class NettyControllerTerminalClosedPacketCallbackImpl implements NettyControllerTerminalClosedPacketCallback {
    @Override
    public void onTerminalClosedPacket(PacketMessage packetMessage) {
        packetMessage.log();

        if (GlobalConfiguration.terminal != null) {
            Terminal terminal = GlobalConfiguration.terminal;
            terminal.closeByOpposite();
        }
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyComputerDisconnectedPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;

/**
 * @author gdrfgdrf
 */
public class NettyComputerDisconnectedPacketCallbackImpl implements NettyComputerDisconnectedPacketCallback {
    @Override
    public void onComputerDisconnectedPacket(PacketMessage packetMessage) throws Exception {
        if (GlobalConfiguration.terminal != null) {
            Terminal terminal = GlobalConfiguration.terminal;
            terminal.closeByOpposite();
        }
    }
}

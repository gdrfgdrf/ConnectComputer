package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyDefaultPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;

/**
 * @author gdrfgdrf
 */
public class NettyDefaultPacketCallbackImpl implements NettyDefaultPacketCallback {
    @Override
    public void onPacket(PacketMessage packetMessage) throws Exception {
        packetMessage.log();
    }
}

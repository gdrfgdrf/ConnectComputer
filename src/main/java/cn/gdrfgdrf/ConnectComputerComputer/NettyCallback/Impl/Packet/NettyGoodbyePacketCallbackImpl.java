package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyGoodbyePacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class NettyGoodbyePacketCallbackImpl implements NettyGoodbyePacketCallback {
    @Override
    public void onGoodbyePacket(PacketMessage packetMessage) throws Exception {
        log.info(AppLocale.NETTY_PACKET_GOODBYE_NOTIFICATION);
    }
}
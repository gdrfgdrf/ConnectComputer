package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.ComputerDisconnectedPacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackInfo;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet.NettyComputerDisconnectedPacketCallbackImpl;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;

/**
 * @author gdrfgdrf
 */
@CallbackInfo(
        targetClass = ComputerDisconnectedPacketHandler.class,
        implementedClass = NettyComputerDisconnectedPacketCallbackImpl.class
)
public interface NettyComputerDisconnectedPacketCallback extends NettyCallback {
    void onComputerDisconnectedPacket(PacketMessage packetMessage) throws Exception;
}

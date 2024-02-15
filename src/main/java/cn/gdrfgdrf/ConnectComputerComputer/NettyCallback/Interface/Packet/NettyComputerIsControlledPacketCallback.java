package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.ComputerIsControlledPacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackInfo;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet.NettyComputerIsControlledPacketCallbackImpl;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;

/**
 * @author gdrfgdrf
 */
@CallbackInfo(
        targetClass = ComputerIsControlledPacketHandler.class,
        implementedClass = NettyComputerIsControlledPacketCallbackImpl.class
)
public interface NettyComputerIsControlledPacketCallback extends NettyCallback {
    void onComputerIsControlledPacket(PacketMessage packetMessage) throws Exception;
}

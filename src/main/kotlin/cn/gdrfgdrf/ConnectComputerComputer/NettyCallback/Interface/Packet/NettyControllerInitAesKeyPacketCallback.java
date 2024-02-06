package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.ControllerInitAesKeyPacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackInfo;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet.NettyControllerInitAesKeyPacketCallbackImpl;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;

/**
 * @author gdrfgdrf
 */
@CallbackInfo(
        targetClass = ControllerInitAesKeyPacketHandler.class,
        implementedClass = NettyControllerInitAesKeyPacketCallbackImpl.class
)
public interface NettyControllerInitAesKeyPacketCallback extends NettyCallback {
    void onInitAesKeyPacket(PacketMessage packetMessage) throws Exception;
}

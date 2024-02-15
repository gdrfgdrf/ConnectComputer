package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.ControllerExchangeRsaPublicKeyPacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackInfo;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet.NettyControllerExchangeRsaPublicKeyPacketCallbackImpl;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;

/**
 * @author gdrfgdrf
 */
@CallbackInfo(
        targetClass = ControllerExchangeRsaPublicKeyPacketHandler.class,
        implementedClass = NettyControllerExchangeRsaPublicKeyPacketCallbackImpl.class
)
public interface NettyControllerExchangeRsaPublicKeyPacketCallback extends NettyCallback {
    void onExchangeRsaPublicKeyPacket(PacketMessage packetMessage) throws Exception;
}

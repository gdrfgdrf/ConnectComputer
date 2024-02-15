package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.GoodbyePacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackInfo;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet.NettyGoodbyePacketCallbackImpl;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;

/**
 * @author gdrfgdrf
 */
@CallbackInfo(
        targetClass = GoodbyePacketHandler.class,
        implementedClass = NettyGoodbyePacketCallbackImpl.class
)
public interface NettyGoodbyePacketCallback extends NettyCallback {
    void onGoodbyePacket(PacketMessage packetMessage) throws Exception;
}

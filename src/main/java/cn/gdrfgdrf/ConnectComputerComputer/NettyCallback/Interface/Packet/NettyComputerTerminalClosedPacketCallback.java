package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.ComputerTerminalClosedPacketHandler;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackInfo;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet.NettyComputerTerminalClosedPacketCallbackImpl;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;

/**
 * @author gdrfgdrf
 */
@CallbackInfo(
        targetClass = ComputerTerminalClosedPacketHandler.class,
        implementedClass = NettyComputerTerminalClosedPacketCallbackImpl.class
)
public interface NettyComputerTerminalClosedPacketCallback extends NettyCallback {
    void onTerminalClosedPacket(PacketMessage packetMessage) throws Exception;
}

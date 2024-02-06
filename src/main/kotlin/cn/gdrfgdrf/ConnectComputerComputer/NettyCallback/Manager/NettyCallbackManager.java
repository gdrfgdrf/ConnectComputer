package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Manager;


import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackClass;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.*;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.*;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;

/**
 * @author gdrfgdrf
 */
@CallbackClass(
        classes = {
                NettyDefaultPacketCallback.class,

                NettyConnectionStateChangeCallback.class,

                NettyInitAesKeyPacketCallback.class,
                NettyLoginPacketCallback.class,
                NettyGoodbyePacketCallback.class,

                NettyControllerExchangeRsaPublicKeyPacketCallback.class,
                NettyControllerInitAesKeyPacketCallback.class,

                NettyComputerIsControlledPacketCallback.class,
                NettyComputerDisconnectedPacketCallback.class,
                NettyControllerDisconnectedPacketCallback.class,
                NettyTerminalResponsePacketCallback.class,
                NettyKeyEventPacketCallback.class,
                NettyControllerTerminalClosedPacketCallback.class,
                NettyComputerTerminalClosedPacketCallback.class
        }
)
public class NettyCallbackManager extends AbstractNettyCallbackManager implements Bean {
    @Override
    public void run() throws Exception {
        super.initNettyCallback();
    }
}

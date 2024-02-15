package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.*;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation.PacketHandlerClass;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import cn.gdrfgdrf.Protobuf.Action.Computer.Security.ComputerSecurityProto;

/**
 * @author gdrfgdrf
 */
@PacketHandlerClass(
        clazz = {
                GoodbyePacketHandler.class,
                HeartPingPongPacketHandler.class,

                InitAesKeyPacketHandler.class,
                LoginPacketHandler.class,
                RegisterComputerPacketHandler.class,
                ComputerStatusChangePacketHandler.class,

                ControllerExchangeRsaPublicKeyPacketHandler.class,
                ControllerInitAesKeyPacketHandler.class,

                ComputerIsControlledPacketHandler.class,
                ControllerDisconnectedPacketHandler.class,
                ComputerDisconnectedPacketHandler.class,
                KeyEventPacketHandler.class,
                TerminalResponsePacketHandler.class,
                ControllerTerminalClosedPacketHandler.class,
                ComputerTerminalClosedPacketHandler.class,

                DefaultPacketHandler.class
        }
)
public class PacketHandlerManager extends AbstractPacketHandlerManager implements Bean {
    @Override
    public void init() throws Exception {
        super.initHandler();
    }
}

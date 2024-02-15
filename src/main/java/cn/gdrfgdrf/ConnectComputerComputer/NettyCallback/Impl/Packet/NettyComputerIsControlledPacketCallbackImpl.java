package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyComputerIsControlledPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.LocalTerminal;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;

/**
 * @author gdrfgdrf
 */
public class NettyComputerIsControlledPacketCallbackImpl implements NettyComputerIsControlledPacketCallback {
    @Override
    public void onComputerIsControlledPacket(PacketMessage packetMessage) throws Exception {
        packetMessage.log();
//        if (GlobalConfiguration.terminal != null) {
//            Terminal terminal = GlobalConfiguration.terminal;
//            terminal.closeByOpposite();
//        }
//
//        Terminal terminal = new LocalTerminal(chars -> {
//            String response = new String(chars);
//
//            ComputerProto.TerminalResponsePacket terminalResponse = ComputerProto.TerminalResponsePacket.newBuilder()
//                    .setResponse(response)
//                    .build();
//
//            try {
//                NettyClient.INSTANCE.getSender().send(terminalResponse);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        terminal.start();
//
//        GlobalConfiguration.terminal = terminal;
    }
}

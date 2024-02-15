package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyTerminalResponsePacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.RemoteTerminal;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;

/**
 * @author gdrfgdrf
 */
public class NettyTerminalResponsePacketCallbackImpl implements NettyTerminalResponsePacketCallback {
    @Override
    public void onTerminalResponsePacket(PacketMessage packetMessage) throws Exception {
        if (GlobalConfiguration.terminal == null) {
            return;
        }

        ComputerProto.TerminalResponsePacket message = packetMessage.getMessage();
        String response = message.getResponse();

        GlobalConfiguration.terminal.userInput(response);
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerComputer.Common.Key.AesKey;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyControllerInitAesKeyPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal;
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.LocalTerminal;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AESUtils;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.Security.ComputerSecurityProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.Security.ControllerSecurityProto;

/**
 * @author gdrfgdrf
 */
public class NettyControllerInitAesKeyPacketCallbackImpl implements NettyControllerInitAesKeyPacketCallback {
    @Override
    public void onInitAesKeyPacket(PacketMessage packetMessage) throws Exception {
        ComputerSecurityProto.AesKeyReceivedPacket aesKeyReceivedPacket =
                ComputerSecurityProto.AesKeyReceivedPacket.newBuilder()
                        .build();

        NettyClient.INSTANCE.getSender().send(
                aesKeyReceivedPacket,
                packetMessage.getPacket().getRequestId(),
                ResultEnum.NETTY_AES_KEY_EXCHANGED
        );

        if (GlobalConfiguration.terminal != null) {
            Terminal terminal = GlobalConfiguration.terminal;
            terminal.closeByOpposite();
        }

        Terminal terminal = new LocalTerminal(chars -> {
            String response = new String(chars);

            ComputerProto.TerminalResponsePacket terminalResponse = ComputerProto.TerminalResponsePacket.newBuilder()
                    .setResponse(response)
                    .build();

            try {
                NettyClient.INSTANCE.getSender().send(terminalResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        terminal.start();

        GlobalConfiguration.terminal = terminal;
    }
}

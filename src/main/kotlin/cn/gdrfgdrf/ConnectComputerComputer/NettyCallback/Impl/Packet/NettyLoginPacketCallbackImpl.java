package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.Account;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.ComputerData;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyLoginPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import lombok.extern.slf4j.Slf4j;

import java.net.InetAddress;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class NettyLoginPacketCallbackImpl implements NettyLoginPacketCallback {
    @Override
    public void onLoginSuccess(PacketMessage packetMessage) throws Exception {
        packetMessage.log();

        DataStore dataStore = BeanManager.getInstance().getBean("DataStore");
        Account account = dataStore.getAccount();
        if (account.getController()) {
            return;
        }
        ComputerData computerData = dataStore.getComputerData();

        String name = "unknown";
        try {
            name = InetAddress.getLocalHost().getHostName();
        } catch (Exception ignored) {
        }

        ComputerProto.RegisterComputerPacket registerComputerPacket = ComputerProto.RegisterComputerPacket.newBuilder()
                .setId(computerData.getId() != null ? computerData.getId() : -1)
                .setName(name)
                .build();
        NettyClient.INSTANCE.getSender().send(registerComputerPacket);
    }

    @Override
    public void onLoginFailed(PacketMessage packetMessage) throws Exception {
        packetMessage.log();
    }
}

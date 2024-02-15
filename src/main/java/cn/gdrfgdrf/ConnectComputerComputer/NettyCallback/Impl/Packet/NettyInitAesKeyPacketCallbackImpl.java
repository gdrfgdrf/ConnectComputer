package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Impl.Packet;

import cn.gdrfgdrf.ConnectComputerComputer.Common.User.User;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.NettyClient;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.Account;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Interface.Packet.NettyInitAesKeyPacketCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message.PacketMessage;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.Protobuf.Action.Account.AccountProto;
import cn.gdrfgdrf.Protobuf.Action.Account.Enum.AccountEnumProto;

/**
 * @author gdrfgdrf
 */
public class NettyInitAesKeyPacketCallbackImpl implements NettyInitAesKeyPacketCallback {
    @Override
    public void onInitAesKeyPacket(PacketMessage packetMessage) throws Exception {
        packetMessage.log();

        DataStore dataStore = BeanManager.getInstance().getBean("DataStore");
        Account account = dataStore.getAccount();

        AccountProto.UserLoginPacket userLoginPacket = AccountProto.UserLoginPacket.newBuilder()
                .setToken(User.INSTANCE.getToken())
                .setLoginMode(account.isController() ?
                        AccountEnumProto.LoginModeEnum.CONTROLLER : AccountEnumProto.LoginModeEnum.COMPUTER)
                .build();

        NettyClient.INSTANCE.getSender().send(userLoginPacket);
    }
}

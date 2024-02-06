package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Handler;

import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Utils.AnyPacketPacker;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Utils.MessageTypeUnpacker;
import cn.gdrfgdrf.ConnectComputerComputer.Common.Key.AesKey;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.Account;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AESUtils;
import cn.gdrfgdrf.Protobuf.Action.Computer.Security.ComputerSecurityProto;
import cn.gdrfgdrf.Protobuf.Action.Controller.Security.ControllerSecurityProto;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.AnyPacketProto;
import cn.gdrfgdrf.Protobuf.Connection.Heart.HeartProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityEnumProto;
import com.google.protobuf.ByteString;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author gdrfgdrf
 */
public class ConnectionEncryptHandler extends MessageToMessageEncoder<BaseProto.Packet> {
    private final Account account;

    {
        DataStore dataStore = BeanManager.getInstance().getBean("DataStore");
        account = dataStore.getAccount();
    }

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, BaseProto.Packet packet, List<Object> out) throws Exception {
        Message message = MessageTypeUnpacker.unpack(packet.getData());
        if (message instanceof HeartProto.HeartPongPacket) {
            out.add(packet);
            return;
        }
        if (GlobalConfiguration.tempAesKey == null) {
            out.add(packet);
            return;
        }

        AesKey aesKey = GlobalConfiguration.tempAesKey;
        byte[] bytes = packet.toByteArray();
        bytes = AESUtils.encryptToByteByCBC(bytes, aesKey.getIv(), aesKey.getKey());

        Message resultMessage;
        if (account.getController()) {
            resultMessage = ControllerSecurityProto.EncryptBlockPacket.newBuilder()
                    .setEncryptAlgorithm(SecurityEnumProto.EncryptAlgorithmEnum.AES)
                    .setEncrypted(ByteString.copyFrom(bytes))
                    .build();
        } else {
            resultMessage = ComputerSecurityProto.EncryptBlockPacket.newBuilder()
                    .setEncryptAlgorithm(SecurityEnumProto.EncryptAlgorithmEnum.AES)
                    .setEncrypted(ByteString.copyFrom(bytes))
                    .build();
        }

        AnyPacketProto.AnyPacket serverResultAnyPacket = AnyPacketPacker.pack(resultMessage);
        BaseProto.Packet serverResult = BaseProto.Packet.newBuilder()
                .setData(serverResultAnyPacket)
                .setRequestId(packet.getRequestId())
                .build();

        out.add(serverResult);
    }
}

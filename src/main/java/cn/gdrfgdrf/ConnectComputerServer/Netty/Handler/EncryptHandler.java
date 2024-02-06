package cn.gdrfgdrf.ConnectComputerServer.Netty.Handler;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.NettyUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.AesKey;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.MessageTypeUnpacker;
import cn.gdrfgdrf.ConnectComputerServer.Utils.AESUtils;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.AnyPacketProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityEnumProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityProto;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.AnyPacketPacker;
import cn.gdrfgdrf.ConnectComputerServer.Utils.RSAUtils;
import com.google.protobuf.ByteString;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @author gdrfgdrf
 */
public class EncryptHandler extends MessageToMessageEncoder<BaseProto.Packet> {
    @Override
    protected void encode(ChannelHandlerContext ctx, BaseProto.Packet msg, List<Object> out) throws Exception {
        Message message = MessageTypeUnpacker.unpack(msg.getData());
        if (message instanceof SecurityProto.EncryptBlockPacket) {
            out.add(msg);
            return;
        }
        if (!NettyServer.getInstance().containsAesKey(ctx.channel())) {
            out.add(msg);
            return;
        }

        AesKey aesKey = NettyServer.getInstance().getAesKey(ctx.channel());
        byte[] bytes = msg.toByteArray();
        bytes = AESUtils.encryptToByteByCBC(bytes, aesKey.getIv(), aesKey.getKey());

        SecurityProto.EncryptBlockPacket encryptBlockPacket = SecurityProto.EncryptBlockPacket.newBuilder()
                .setEncryptAlgorithm(SecurityEnumProto.EncryptAlgorithmEnum.AES)
                .setEncrypted(ByteString.copyFrom(bytes))
                .build();
        AnyPacketProto.AnyPacket anyPacket = AnyPacketPacker.pack(encryptBlockPacket);

        BaseProto.Packet basePacket = BaseProto.Packet.newBuilder()
                .setData(anyPacket)
                .build();

        out.add(basePacket);
    }
}

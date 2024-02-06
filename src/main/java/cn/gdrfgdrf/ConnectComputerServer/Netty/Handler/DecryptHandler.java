package cn.gdrfgdrf.ConnectComputerServer.Netty.Handler;

import cn.gdrfgdrf.ConnectComputerServer.Enum.RSAKeyEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.AesKey;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.NettyServerUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.AESUtils;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Connection.Heart.HeartProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityEnumProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityProto;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.MessageTypeUnpacker;
import cn.gdrfgdrf.ConnectComputerServer.Utils.RSAUtils;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * @author gdrfgdrf
 */
public class DecryptHandler extends MessageToMessageDecoder<BaseProto.Packet> {
    @Override
    protected void decode(ChannelHandlerContext ctx, BaseProto.Packet msg, List<Object> out) throws Exception {
        Message message = MessageTypeUnpacker.unpack(msg.getData());
        if (!(message instanceof SecurityProto.EncryptBlockPacket encryptBlockPacket)) {
            out.add(msg);
            return;
        }

        byte[] bytes = encryptBlockPacket.getEncrypted().toByteArray();
        if (encryptBlockPacket.getEncryptAlgorithm() == SecurityEnumProto.EncryptAlgorithmEnum.RSA) {
            bytes = RSAUtils.privateDecryptToByte(bytes, RSAKeyEnum.NETTY_KEY.getPrivateKey());
        }
        if (encryptBlockPacket.getEncryptAlgorithm() == SecurityEnumProto.EncryptAlgorithmEnum.AES) {
            if (!NettyServer.getInstance().containsAesKey(ctx.channel())) {
                return;
            }
            AesKey aesKey = NettyServer.getInstance().getAesKey(ctx.channel());
            bytes = AESUtils.decryptToByteByCBC(bytes, aesKey.getIv(), aesKey.getKey());
        }

        BaseProto.Packet basePacket = BaseProto.Packet.newBuilder()
                .mergeFrom(bytes)
                .build();

        out.add(basePacket);
    }
}

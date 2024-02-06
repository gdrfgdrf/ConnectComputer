package cn.gdrfgdrf.ConnectComputerServer.Netty.PacketHandler;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.ClientVersion;
import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.Handler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.AesKey;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Common.Writer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Enum.ConditionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.AnyPacketPacker;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Utils.AESUtils;
import cn.gdrfgdrf.ConnectComputerServer.Utils.RSAUtils;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.AnyPacketProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityEnumProto;
import cn.gdrfgdrf.Protobuf.Security.SecurityProto;
import com.google.protobuf.ByteString;
import io.netty.channel.Channel;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.helpers.MessageFormatter;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.PublicKey;

/**
 * @author gdrfgdrf
 */
@Component
@Condition(
        condition = {
                ConditionEnum.AES_KEY_HAS_BEEN_GENERATED
        },
        notSatisfiedResult = {
                ResultEnum.NETTY_AES_KEY_HAS_BEEN_GENERATED
        },
        need = {
                false
        }
)
@Handler(support = SecurityProto.ExchangeRsaPublicKeyPacket.class)
@ClientVersion(version = VersionEnum.v1_0_0)
public class ExchangeRsaPublicKeyPacketHandler implements PacketHandler<SecurityProto.ExchangeRsaPublicKeyPacket> {
    @Override
    public void handle(Channel channel, BaseProto.Packet packet, SecurityProto.ExchangeRsaPublicKeyPacket message) throws Exception {
        PublicKey publicKey = RSAUtils.getPublicKey(message.getPublicKey());

        IvParameterSpec iv = AESUtils.generateIv();
        SecretKey key = AESUtils.generateKey();
        AesKey aesKey = new AesKey(iv, key);

        SecurityProto.InitAesKeyPacket initAesKeyPacket = SecurityProto.InitAesKeyPacket.newBuilder()
                .setIv(Base64.encodeBase64String(iv.getIV()))
                .setKey(Base64.encodeBase64String(key.getEncoded()))
                .build();

        ResultEnum resultEnum = ResultEnum.NETTY_AES_KEY_IS_GENERATED;

        AnyPacketProto.AnyPacket anyPacket = AnyPacketPacker.pack(initAesKeyPacket);
        BaseProto.Packet resultPacket = BaseProto.Packet.newBuilder()
                .setCode(resultEnum.getCode())
                .setMessage(
                        MessageFormatter.arrayFormat(
                                resultEnum.getMessageEnum().toString(),
                                null
                        ).getMessage()
                )
                .setData(anyPacket)
                .build();

        byte[] bytes = resultPacket.toByteArray();
        bytes = RSAUtils.publicEncryptToByte(bytes, publicKey);
        SecurityProto.EncryptBlockPacket encryptBlockPacket = SecurityProto.EncryptBlockPacket.newBuilder()
                .setEncryptAlgorithm(SecurityEnumProto.EncryptAlgorithmEnum.RSA)
                .setEncrypted(ByteString.copyFrom(bytes))
                .build();

        Writer.write(
                channel,
                encryptBlockPacket,
                packet.getRequestId(),
                ResultEnum.NETTY_AES_KEY_IS_GENERATED
        );

        NettyServer.getInstance().addAesKey(channel, aesKey);
    }
}

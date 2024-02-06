package cn.gdrfgdrf.ConnectComputerServer.Netty.Handler;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.ClientVersion;
import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation.PassNettyHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Exception.IllegalParameterNettyException;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.MessageTypeUnpacker;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.ExternalErrorProto;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Message;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.annotation.AnnotatedElementUtils;

import java.lang.annotation.Annotation;

/**
 * @author gdrfgdrf
 */
public class ClientVersionHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object obj) throws Exception {
        if (!(obj instanceof BaseProto.Packet packet)) {
            ctx.fireChannelRead(obj);
            return;
        }
        boolean result = true;

        Class<? extends PacketHandler> handlerClass = getHandlerClass(packet);
        ClientVersion clientVersion = AnnotatedElementUtils.findMergedAnnotation(handlerClass, ClientVersion.class);
        if (clientVersion != null) {
            result = processClientVersion(packet, clientVersion);
        }

        if (result) {
            ctx.fireChannelRead(obj);
        }
    }

    private boolean processClientVersion(BaseProto.Packet packet, ClientVersion clientVersion) {
        String packetClientVersion = packet.getClientVersion();
        if (StringUtils.isBlank(packetClientVersion)) {
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_PARAMETER,
                    ExternalErrorProto.ParameterErrorPacket.class,
                    packet.getRequestId(),
                    false
            );
        }

        VersionEnum clientVersionEnum = clientVersion.version();
        VersionEnum packetClientVersionEnum;

        try {
            packetClientVersionEnum = VersionEnum.valueOf(packetClientVersion);
        } catch (Exception ignored) {
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_PARAMETER,
                    ExternalErrorProto.ParameterErrorPacket.class,
                    packet.getRequestId(),
                    false
            );
        }
        if (!VersionEnum.compare(clientVersionEnum, packetClientVersionEnum)) {
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_CLIENT_VERSION_ILLEGAL,
                    ExternalErrorProto.ClientVersionTooLowPacket.class,
                    packet.getRequestId(),
                    false
            );
        }

        return true;
    }

    public Class<? extends PacketHandler> getHandlerClass(BaseProto.Packet packet) throws InvalidProtocolBufferException {
        Message result = MessageTypeUnpacker.unpack(packet.getData());
        PacketHandler packetHandler = NettyServer.getInstance().HANDLER.get(result.getClass());
        if (packetHandler == null) {
            return null;
        }

        return packetHandler.getClass();
    }
}

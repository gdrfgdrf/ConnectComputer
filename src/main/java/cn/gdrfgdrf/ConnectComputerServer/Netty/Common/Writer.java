package cn.gdrfgdrf.ConnectComputerServer.Netty.Common;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Exception.IllegalParameterNettyException;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.AnyPacketProto;
import cn.gdrfgdrf.Protobuf.Common.InternalErrorProto;
import cn.gdrfgdrf.Protobuf.Connection.Heart.HeartProto;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Utils.AnyPacketPacker;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultCode;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import com.google.protobuf.GeneratedMessage;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import org.slf4j.helpers.MessageFormatter;

import java.lang.reflect.Method;

/**
 * @author gdrfgdrf
 */
public class Writer {
    public static ChannelFuture error(Channel channel, GeneratedMessage msg, String requestId) {
        return write(channel, msg, requestId, ResultEnum.ERROR);
    }

    public static ChannelFuture write(Channel channel, IllegalParameterNettyException exception) {
        try {
            ResultEnum resultEnum = exception.getResultEnum();
            BaseProto.Packet.Builder builder = getBasePacket(exception.getPacket()).toBuilder()
                    .setCode(resultEnum.getCode())
                    .setMessage(
                            MessageFormatter.arrayFormat(
                                    resultEnum.getMessageEnum().toString(),
                                    exception.getPlaceholders()
                            ).getMessage()
                    );
            if (exception.getRequestId() != null) {
                builder.setRequestId(exception.getRequestId());
            }

            return write(channel, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ChannelFuture write(
            Channel channel,
            Class<? extends GeneratedMessage> msg,
            String requestId,
            ResultEnum resultEnum,
            Object... placeholders
    ) {
        GeneratedMessage packet;

        try {
            Method newBuilder = msg.getMethod("newBuilder");
            GeneratedMessage.Builder<?> builder = (GeneratedMessage.Builder<?>) newBuilder.invoke(null);
            packet = (GeneratedMessage) builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            packet = InternalErrorProto.ErrorPacket.newBuilder().build();
        }

        return write(channel, packet, requestId, resultEnum, placeholders);
    }

    public static ChannelFuture write(
            Channel channel,
            GeneratedMessage msg,
            String requestId,
            ResultEnum resultEnum,
            Object... placeholders
    ) {
        try {
            BaseProto.Packet.Builder builder = getBasePacket(msg).toBuilder()
                    .setCode(resultEnum.getCode())
                    .setMessage(
                            MessageFormatter.arrayFormat(
                                    resultEnum.getMessageEnum().toString(),
                                    placeholders
                            ).getMessage()
                    );
            if (requestId != null) {
                builder.setRequestId(requestId);
            }

            return write(channel, builder.build());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ChannelFuture write(Channel channel, HeartProto.HeartPingPacket msg) {
        try {
            BaseProto.Packet basePacket = getBasePacket(msg).toBuilder()
                    .setCode(ResultCode.SUCCESS)
                    .setMessage("Ping")
                    .build();

            return write(channel, basePacket);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ChannelFuture write(Channel channel, BaseProto.Packet msg) {
        return channel.writeAndFlush(msg);
    }

    private static BaseProto.Packet getBasePacket(GeneratedMessage data) {
        AnyPacketProto.AnyPacket anyPacket = AnyPacketPacker.pack(data);
        BaseProto.Packet.Builder builder = BaseProto.Packet.newBuilder()
                .setData(anyPacket);

        return builder.build();
    }

}

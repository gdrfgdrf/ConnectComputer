package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Result.ResultEnum;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.AnyPacketProto;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.Synchronization.SyncFuture;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.SyncResponsePacketPoster.SyncResponsePacketPoster;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Utils.AnyPacketPacker;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.tuple.Pair;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @author gdrfgdrf
 */
public class Writer {
    public static ChannelFuture write(Channel channel, BaseProto.Packet msg) {
        return channel.writeAndFlush(msg);
    }

    public static ChannelFuture write(Channel channel, GeneratedMessageV3 msg, String requestId) {
        return write(channel, msg, requestId, null);
    }

    public static ChannelFuture write(Channel channel, GeneratedMessageV3 msg, String requestId, ResultEnum resultEnum) {
        try {
            if (requestId == null) {
                requestId = RandomStringUtils.randomAlphabetic(16);
            }
            return write(channel, getBasePacket(msg, requestId, resultEnum));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Pair<BaseProto.Packet, Message> writeSynchronously(
            Channel channel,
            GeneratedMessageV3 msg,
            SyncFuture<Pair<BaseProto.Packet, Message>> syncFuture,
            String requestId,
            ResultEnum resultEnum,
            ChannelFutureListener channelFutureListener
    ) throws ExecutionException, InterruptedException, TimeoutException {
        if (requestId == null) {
            requestId = RandomStringUtils.randomAlphabetic(16);
        }
        BaseProto.Packet packet = getBasePacket(msg, requestId, resultEnum);

        ChannelFuture channelFuture = write(channel, packet);
        if (channelFutureListener != null) {
            channelFuture.addListener(channelFutureListener);
        }
        SyncResponsePacketPoster.INSTANCE.registerSynchronizedPacket(packet, syncFuture);

        return syncFuture.get();
    };

    private static BaseProto.Packet getBasePacket(GeneratedMessageV3 data, String requestId, ResultEnum resultEnum) {
        AnyPacketProto.AnyPacket anyPacket = AnyPacketPacker.pack(data);
        BaseProto.Packet.Builder builder = BaseProto.Packet.newBuilder()
                .setRequestId(requestId)
                .setData(anyPacket);
        if (resultEnum != null) {
            builder.setCode(resultEnum.getCode());
            builder.setMessage(resultEnum.getMessage());
        }
        return builder.build();
    }
}

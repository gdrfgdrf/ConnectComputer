package cn.gdrfgdrf.ConnectComputerServer.Netty.Base;

import cn.gdrfgdrf.Protobuf.BaseProto;
import com.google.protobuf.GeneratedMessageV3;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
public interface PacketHandler<T> {
    void handle(Channel channel, BaseProto.Packet packet, T message) throws Exception;
}

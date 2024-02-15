package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base;

import cn.gdrfgdrf.Protobuf.BaseProto;
import com.google.protobuf.Message;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
public interface BasePacketHandler {
    void handle(Channel channel, BaseProto.Packet packet, Message message) throws Exception;
}


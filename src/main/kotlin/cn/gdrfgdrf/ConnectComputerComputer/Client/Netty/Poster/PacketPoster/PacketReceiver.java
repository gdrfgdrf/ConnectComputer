package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.PacketPoster;

import cn.gdrfgdrf.Protobuf.BaseProto;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;

/**
 * @author gdrfgdrf
 */
public interface PacketReceiver {
    Class<? extends GeneratedMessageV3>[] type();
    void onReceived(BaseProto.Packet packet, Message message);
}

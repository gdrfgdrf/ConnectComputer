package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Message;

import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import com.google.protobuf.Message;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Data
@Slf4j
public class PacketMessage {
    private BaseProto.Packet packet;
    private Message message;

    public void log() {
        if (packet.getCode() == Constants.SUCCESS) {
            log.info(packet.getMessage());
            return;
        }
        log.error(packet.getMessage());
    }

    public static PacketMessage createByMessage(BaseProto.Packet packet, Message message) {
        PacketMessage packetMessage = new PacketMessage();
        packetMessage.setPacket(packet);
        packetMessage.setMessage(message);
        return packetMessage;
    }

    public <T> T getMessage() {
        return (T) message;
    }
}

package cn.gdrfgdrf.ConnectComputerServer.Result.Serializer;

import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

/**
 * @author gdrfgdrf
 */
public class MessageEnumSerializer extends JsonSerializer<MessageEnum> {
    @Override
    public void serialize(MessageEnum value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        if (value == null) {
            gen.writeNull();
            return;
        }
        gen.writeString(value.name());
    }
}

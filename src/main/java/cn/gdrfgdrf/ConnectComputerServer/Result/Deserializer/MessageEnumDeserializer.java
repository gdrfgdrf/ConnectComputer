package cn.gdrfgdrf.ConnectComputerServer.Result.Deserializer;

import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import cn.gdrfgdrf.ConnectComputerServer.Utils.StringUtils;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;

/**
 * @author gdrfgdrf
 */
public class MessageEnumDeserializer extends JsonDeserializer<MessageEnum> {
    @Override
    public MessageEnum deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException {
        String s = jsonParser.readValueAs(String.class);
        if (StringUtils.isBlank(s)) {
            return null;
        }
        return MessageEnum.valueOf(s);
    }
}

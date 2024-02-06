package cn.gdrfgdrf.ConnectComputerServer.Result.Deserializer;

import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import com.alibaba.fastjson2.JSONReader;
import com.alibaba.fastjson2.reader.ObjectReader;

import java.lang.reflect.Type;

/**
 * @author gdrfgdrf
 */
public class MessageEnumDeserializer implements ObjectReader<MessageEnum> {
    public static final MessageEnumDeserializer INSTANCE = new MessageEnumDeserializer();

    @Override
    public MessageEnum readObject(JSONReader jsonReader, Type fieldType, Object fieldName, long features) {
        if (jsonReader.nextIfNull()) {
            return null;
        }

        String message = jsonReader.readString();
        return MessageEnum.valueOf(message);
    }
}

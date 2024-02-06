package cn.gdrfgdrf.ConnectComputerServer.Result.Serializer;

import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import com.alibaba.fastjson2.JSONWriter;
import com.alibaba.fastjson2.writer.ObjectWriter;

import java.lang.reflect.Type;

/**
 * @author gdrfgdrf
 */
public class MessageEnumSerializer implements ObjectWriter<MessageEnum> {
    public static final MessageEnumSerializer INSTANCE = new MessageEnumSerializer();

    @Override
    public void write(JSONWriter jsonWriter, Object object, Object fieldName, Type fieldType, long features) {
        if (object == null) {
            jsonWriter.writeNull();
        }

        MessageEnum messageEnum = (MessageEnum) object;
        jsonWriter.writeString(messageEnum.name());
    }
}

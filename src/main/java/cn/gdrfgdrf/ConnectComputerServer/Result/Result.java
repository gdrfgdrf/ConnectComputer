package cn.gdrfgdrf.ConnectComputerServer.Result;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import cn.gdrfgdrf.ConnectComputerServer.Result.Deserializer.MessageEnumDeserializer;
import cn.gdrfgdrf.ConnectComputerServer.Result.Serializer.MessageEnumSerializer;
import com.alibaba.fastjson2.annotation.JSONField;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@Data
public class Result {
    private Integer code;
    private String message;
    @JSONField(serializeUsing = MessageEnumSerializer.class, deserializeUsing = MessageEnumDeserializer.class)
    private MessageEnum messageEnum;
    private Map<String, Information> data = new HashMap<>();

    public Result setFromResultEnum(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessageEnum().toString();
        this.messageEnum = resultEnum.getMessageEnum();
        return this;
    }

    public void addData(Information data) {
        data.setClassName(data.getClass().getSimpleName());
        this.data.put(data.getClass().getSimpleName(), data);
    }
}

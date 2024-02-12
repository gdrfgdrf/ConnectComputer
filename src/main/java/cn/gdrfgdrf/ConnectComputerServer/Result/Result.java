package cn.gdrfgdrf.ConnectComputerServer.Result;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import cn.gdrfgdrf.ConnectComputerServer.Result.Deserializer.MessageEnumDeserializer;
import cn.gdrfgdrf.ConnectComputerServer.Result.Serializer.MessageEnumSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
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
    @JsonSerialize(using = MessageEnumSerializer.class)
    @JsonDeserialize(using = MessageEnumDeserializer.class)
    private MessageEnum messageEnum;
    private Map<String, Information> data = new HashMap<>();

    public Result setFromResultEnum(ResultEnum resultEnum) {
        this.code = resultEnum.getCode();
        this.message = resultEnum.getMessageEnum().toString();
        this.messageEnum = resultEnum.getMessageEnum();
        return this;
    }

    public void addData(Information data) {
        this.data.put(data.getClass().getSimpleName(), data);
    }
}

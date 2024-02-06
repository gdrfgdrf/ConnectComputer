package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import com.alibaba.fastjson2.JSONObject;
import com.alibaba.fastjson2.annotation.JSONField;

import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@ToString
public class ParseResult {
    @JSONField(serialize = false, deserialize = false)
    private Boolean success;

    private Integer code;
    private String message;
    private Map<String, Information> data = new HashMap<>();

    @JSONField(serialize = false, deserialize = false)
    private JSONObject rawJson;

    public Boolean getSuccess() {
        return code == Constants.SUCCESS;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String, Information> getData() {
        return data;
    }

    public void setData(Map<String, Information> data) {
        this.data = data;
    }

    public <T> T getInformation(Class<T> clazz) {
        return (T) data.get(clazz.getSimpleName());
    }

    public JSONObject getRawJson() {
        return rawJson;
    }

    public void setRawJson(JSONObject rawJson) {
        this.rawJson = rawJson;
    }
}

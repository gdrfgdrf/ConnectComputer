package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.Jackson.SuperJsonNode;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.ToString;

import java.util.HashMap;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ParseResult {
    @JsonIgnore
    private Boolean success;

    private Integer code;
    private String message;
    private Map<String, Information> data = new HashMap<>();

    @JsonIgnore
    private SuperJsonNode rawJson;

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

    public SuperJsonNode getRawJson() {
        return rawJson;
    }

    public void setRawJson(SuperJsonNode rawJson) {
        this.rawJson = rawJson;
    }
}

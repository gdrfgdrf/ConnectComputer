/*
 * Copyright (C) 2024 Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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

    public Boolean isSuccess() {
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

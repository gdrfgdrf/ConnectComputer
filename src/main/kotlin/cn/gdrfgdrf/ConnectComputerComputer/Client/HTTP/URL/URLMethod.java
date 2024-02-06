package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.URL;

import lombok.Getter;
import lombok.ToString;

/**
 * @author gdrfgdrf
 */
@Getter
@ToString
public enum URLMethod {
    GET("GET"),
    POST("POST"),
    PUT("PUT");

    public final String method;

    URLMethod(String method) {
        this.method = method;
    }
}

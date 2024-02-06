package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.URL;

import lombok.Getter;

/**
 * @author gdrfgdrf
 */
@Getter
public enum URLRequestMappingEnum {
    SERVER_INFO_REQUEST_MAPPING("/info"),
    USER_REQUEST_MAPPING("/user"),
    COMPUTER_REQUEST_MAPPING("/computer");

    private final String mapping;

    URLRequestMappingEnum(String mapping) {
        this.mapping = mapping;
    }
}

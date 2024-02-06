package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.User;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserSecretInformation extends UserInformation {
    private String username;
    private String token;

    private Integer nettyServerPort;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getNettyServerPort() {
        return nettyServerPort;
    }

    public void setNettyServerPort(Integer nettyServerPort) {
        this.nettyServerPort = nettyServerPort;
    }
}

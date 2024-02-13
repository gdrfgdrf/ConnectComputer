package cn.gdrfgdrf.ConnectComputerComputer.Data.Bean;

import cn.gdrfgdrf.ConnectComputerComputer.Data.DataBean;
import lombok.Data;

/**
 * @author gdrfgdrf
 */
@Data
public class ServerInfo implements DataBean {
    private Boolean sslEnabled;
    private String ip;
    private Integer port;

    public String httpProtocol() {
        return sslEnabled ? "https://" : "http://";
    }

    public Boolean getSslEnabled() {
        return sslEnabled;
    }

    public void setSslEnabled(Boolean sslEnabled) {
        this.sslEnabled = sslEnabled;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public void reset() {
        sslEnabled = null;
        ip = null;
        port = null;
    }
}

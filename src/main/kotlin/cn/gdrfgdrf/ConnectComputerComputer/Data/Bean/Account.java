package cn.gdrfgdrf.ConnectComputerComputer.Data.Bean;

import cn.gdrfgdrf.ConnectComputerComputer.Data.DataBean;

/**
 * @author gdrfgdrf
 */
public class Account implements DataBean {
    private String username;
    private String password;
    private Boolean autoLogin;
    private Boolean controller;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean isAutoLogin() {
        return autoLogin;
    }

    public void setAutoLogin(Boolean autoLogin) {
        this.autoLogin = autoLogin;
    }

    public Boolean isController() {
        return controller;
    }

    public void setController(Boolean controller) {
        this.controller = controller;
    }
}

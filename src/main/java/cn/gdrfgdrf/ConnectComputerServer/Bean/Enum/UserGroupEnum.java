package cn.gdrfgdrf.ConnectComputerServer.Bean.Enum;

import java.io.Serializable;

/**
 * @author gdrfgdrf
 */

public enum UserGroupEnum implements Serializable {
    USER("user"),
    ADMIN("admin");

    private final String group;

    UserGroupEnum(String group) {
        this.group = group;
    }

    public String getGroup() {
        return group;
    }
}

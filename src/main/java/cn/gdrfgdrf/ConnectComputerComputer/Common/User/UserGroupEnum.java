package cn.gdrfgdrf.ConnectComputerComputer.Common.User;

import java.io.Serializable;

import lombok.Getter;
import lombok.ToString;

/**
 * @author gdrfgdrf
 */
@Getter
@ToString
public enum UserGroupEnum implements Serializable {
    USER("user"),
    ADMIN("admin");

    private final String group;

    UserGroupEnum(String group) {
        this.group = group;
    }

    public static UserGroupEnum getFromString(String str) {
        if (UserGroupEnum.USER.getGroup().equals(str)) {
            return UserGroupEnum.USER;
        }

        if (UserGroupEnum.ADMIN.getGroup().equals(str)) {
            return UserGroupEnum.ADMIN;
        }

        return UserGroupEnum.USER;
    }
}

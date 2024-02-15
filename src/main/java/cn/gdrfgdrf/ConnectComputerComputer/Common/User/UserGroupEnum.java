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

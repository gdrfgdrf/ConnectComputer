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

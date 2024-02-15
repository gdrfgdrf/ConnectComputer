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

package cn.gdrfgdrf.ConnectComputerComputer.Utils;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;

import java.util.Map;

/**
 * @author gdrfgdrf
 */
public class NetworkUtils {
    private NetworkUtils() {}

    public static void closeNettyThoroughly(Channel channel) {
        if (channel == null) {
            return;
        }

        closeNettyChannel(channel);
        channel.close();
        channel.eventLoop().shutdownGracefully();
    }

    private static void closeNettyChannel(Channel channel) {
        if (channel == null) {
            return;
        }

        Map<String, ChannelHandler> map = channel.pipeline().toMap();
        for (Map.Entry<String, ChannelHandler> entry : map.entrySet()) {
            channel.pipeline().remove(entry.getKey());
        }

        channel.close();
        channel.eventLoop().shutdownGracefully();
    }

    public static boolean isValidPort(String str) {
        if (!StringUtils.checkStringForInteger(str)) {
            return false;
        }

        int port = Integer.parseInt(str);
        return isValidPort(port);
    }

    public static boolean isValidPort(Integer i) {
        if (i == null) {
            return false;
        }
        if (i < 0) {
            return false;
        }
        return i <= 65535;
    }

}

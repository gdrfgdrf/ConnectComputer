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

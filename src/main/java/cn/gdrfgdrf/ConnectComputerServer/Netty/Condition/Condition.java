package cn.gdrfgdrf.ConnectComputerServer.Netty.Condition;

import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
public interface Condition {
    boolean condition(Channel channel);
}

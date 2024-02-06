package cn.gdrfgdrf.ConnectComputerServer.Netty.Condition.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Condition.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
public class AesKeyHasBeenGeneratedCondition implements Condition {
    @Override
    public boolean condition(Channel channel) {
        return NettyServer.getInstance().containsAesKey(channel);
    }
}

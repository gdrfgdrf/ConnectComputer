package cn.gdrfgdrf.ConnectComputerServer.Netty.Condition.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Condition.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
public class ComputerIsControlledCondition implements Condition {
    @Override
    public boolean condition(Channel channel) {
        ComputerUser computerUser = NettyServer.getInstance().getUser(channel);
        return computerUser.getControllerUser() != null;
    }
}

package cn.gdrfgdrf.ConnectComputerServer.Netty.Condition.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.NettyUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Condition.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
public class LoginModeIsComputerCondition implements Condition {
    @Override
    public boolean condition(Channel channel) {
        NettyUser nettyUser = NettyServer.getInstance().getUser(channel);
        return nettyUser instanceof ComputerUser;
    }
}

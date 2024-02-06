package cn.gdrfgdrf.ConnectComputerServer.Netty.Condition.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Condition.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
public class ControllerExchangedConnectionRsaKeyCondition implements Condition {
    @Override
    public boolean condition(Channel channel) {
        ControllerUser controllerUser = NettyServer.getInstance().getUser(channel);
        return controllerUser.isExchangeConnectionRsaKey();
    }
}

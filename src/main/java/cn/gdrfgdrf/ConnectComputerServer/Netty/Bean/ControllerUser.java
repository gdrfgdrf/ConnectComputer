package cn.gdrfgdrf.ConnectComputerServer.Netty.Bean;

import io.netty.channel.Channel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
public class ControllerUser extends NettyUser {
    @Setter
    @Getter
    private ComputerUser computerUser;
    @Setter
    @Getter
    private boolean exchangeConnectionRsaKey = false;

    public ControllerUser(Channel channel, Integer id, String token) {
        super(channel, id, token);
    }
}

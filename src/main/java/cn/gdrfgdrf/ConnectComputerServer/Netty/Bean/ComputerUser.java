package cn.gdrfgdrf.ConnectComputerServer.Netty.Bean;

import io.netty.channel.Channel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
public class ComputerUser extends NettyUser {
    @Setter
    @Getter
    private Integer computerId;
    @Setter
    @Getter
    private String name;
    @Setter
    @Getter
    private ControllerUser controllerUser;
    @Setter
    @Getter
    private boolean exchangeConnectionRsaKey = false;

    public ComputerUser(Channel channel, Integer id, String token) {
        super(channel, id, token);
    }
}

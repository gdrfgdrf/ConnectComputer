package cn.gdrfgdrf.ConnectComputerServer.Netty.Bean;

import io.netty.channel.Channel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.security.PublicKey;

/**
 * @author gdrfgdrf
 */
@Getter
@EqualsAndHashCode
public class NettyUser {
    @Setter
    private Channel channel;

    private final Integer id;
    private final String token;

    protected NettyUser(Channel channel, Integer id, String token) {
        this.channel = channel;
        this.id = id;
        this.token = token;
    }
}

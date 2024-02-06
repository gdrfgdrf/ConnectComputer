package cn.gdrfgdrf.ConnectComputerServer.Netty.Event;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @author gdrfgdrf
 */
public class ComputerOfflineEvent extends ApplicationEvent {
    @Getter
    private final ComputerUser computerUser;

    public ComputerOfflineEvent(Object source, ComputerUser computerUser) {
        super(source);
        this.computerUser = computerUser;
    }

    public ComputerOfflineEvent(Object source, Clock clock, ComputerUser computerUser) {
        super(source, clock);
        this.computerUser = computerUser;
    }
}

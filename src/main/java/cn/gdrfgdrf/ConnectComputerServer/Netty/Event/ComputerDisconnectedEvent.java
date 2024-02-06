package cn.gdrfgdrf.ConnectComputerServer.Netty.Event;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @author gdrfgdrf
 */
public class ComputerDisconnectedEvent extends ApplicationEvent {
    @Getter
    private final ControllerUser controllerUser;

    public ComputerDisconnectedEvent(Object source, ControllerUser controllerUser) {
        super(source);
        this.controllerUser = controllerUser;
    }

    public ComputerDisconnectedEvent(Object source, Clock clock, ControllerUser controllerUser) {
        super(source, clock);
        this.controllerUser = controllerUser;
    }
}

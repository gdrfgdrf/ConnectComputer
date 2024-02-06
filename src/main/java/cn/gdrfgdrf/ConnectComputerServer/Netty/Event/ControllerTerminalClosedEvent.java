package cn.gdrfgdrf.ConnectComputerServer.Netty.Event;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ComputerUser;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @author gdrfgdrf
 */
public class ControllerTerminalClosedEvent extends ApplicationEvent {
    @Getter
    private final ComputerUser computerUser;
    @Getter
    private final ControllerProto.TerminalClosedPacket message;

    public ControllerTerminalClosedEvent(Object source, ComputerUser computerUser, ControllerProto.TerminalClosedPacket message) {
        super(source);
        this.computerUser = computerUser;
        this.message = message;
    }

    public ControllerTerminalClosedEvent(Object source, Clock clock, ComputerUser computerUser, ControllerProto.TerminalClosedPacket message) {
        super(source, clock);
        this.computerUser = computerUser;
        this.message = message;
    }
}

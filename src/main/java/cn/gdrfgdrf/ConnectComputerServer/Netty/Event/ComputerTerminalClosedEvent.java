package cn.gdrfgdrf.ConnectComputerServer.Netty.Event;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Bean.ControllerUser;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;

import java.time.Clock;

/**
 * @author gdrfgdrf
 */
public class ComputerTerminalClosedEvent extends ApplicationEvent {
    @Getter
    private final ControllerUser controllerUser;
    @Getter
    private final ComputerProto.TerminalClosedPacket message;

    public ComputerTerminalClosedEvent(Object source, ControllerUser controllerUser, ComputerProto.TerminalClosedPacket message) {
        super(source);
        this.controllerUser = controllerUser;
        this.message = message;
    }

    public ComputerTerminalClosedEvent(Object source, Clock clock, ControllerUser controllerUser, ComputerProto.TerminalClosedPacket message) {
        super(source, clock);
        this.controllerUser = controllerUser;
        this.message = message;
    }
}

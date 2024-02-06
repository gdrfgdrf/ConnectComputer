package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation;

import java.lang.annotation.*;

import com.google.protobuf.GeneratedMessageV3;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketHandler {
    Class<? extends GeneratedMessageV3>[] support();
}

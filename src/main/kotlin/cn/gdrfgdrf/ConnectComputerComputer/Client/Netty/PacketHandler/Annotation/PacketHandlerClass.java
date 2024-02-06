package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Base.BasePacketHandler;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PacketHandlerClass {
    Class<? extends BasePacketHandler>[] clazz();
}

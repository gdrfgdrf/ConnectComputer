package cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation;

import io.netty.channel.ChannelHandlerAdapter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface PassNettyHandler {
    Class<? extends ChannelHandlerAdapter>[] pass();
}

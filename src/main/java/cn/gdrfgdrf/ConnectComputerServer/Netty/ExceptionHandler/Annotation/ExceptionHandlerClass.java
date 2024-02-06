package cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Annotation;

import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Base.ExceptionHandler;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandlerClass {
    Class<? extends ExceptionHandler>[] classes();
}

package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Handler.ExceptionHandler;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ExceptionHandlerClass {
    Class<? extends ExceptionHandler>[] classes();
}

package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation;

import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface CallbackClass {
    Class<? extends NettyCallback>[] classes();
}

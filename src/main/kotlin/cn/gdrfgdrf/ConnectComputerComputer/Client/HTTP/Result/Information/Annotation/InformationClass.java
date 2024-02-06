package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Annotation;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface InformationClass {
    Class<? extends Information>[] classes();
}

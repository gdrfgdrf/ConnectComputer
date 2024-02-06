package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Annotation;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.ArgumentAssignor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArgumentAssignorClass {
    Class<? extends ArgumentAssignor>[] classes();
}

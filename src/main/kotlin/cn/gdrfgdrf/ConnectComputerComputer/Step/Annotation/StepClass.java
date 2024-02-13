package cn.gdrfgdrf.ConnectComputerComputer.Step.Annotation;

import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface StepClass {
    Class<? extends Step>[] classes();
    String[] routes();
}

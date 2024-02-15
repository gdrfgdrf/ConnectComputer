package cn.gdrfgdrf.ConnectComputerComputer.Bean.Annotation;

import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface BeanOrder {
    Class<? extends Bean>[] order();
}

package cn.gdrfgdrf.ConnectComputerComputer.Menu.Annotation;

import cn.gdrfgdrf.ConnectComputerComputer.Menu.Menu;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface MenuClass {
    Class<? extends Menu>[] classes();
    String[] routes();
}

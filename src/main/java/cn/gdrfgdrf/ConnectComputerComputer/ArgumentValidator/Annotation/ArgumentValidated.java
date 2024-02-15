package cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Annotation;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.ArgumentValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ArgumentValidated {
    Class<? extends ArgumentValidator> validator();
}

package cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Annotation;

import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Base.Validator;

import java.lang.annotation.*;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(SuperValidated.class)
public @interface Validated {
    Class<? extends Validator>[] validator();
}

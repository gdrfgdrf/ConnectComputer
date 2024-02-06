package cn.gdrfgdrf.ConnectComputerServer.Netty.Annotation;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Enum.ConditionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {
    ConditionEnum[] condition();
    ResultEnum[] notSatisfiedResult();
    boolean[] need() default {};
}

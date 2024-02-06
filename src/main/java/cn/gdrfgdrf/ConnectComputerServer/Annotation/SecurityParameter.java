package cn.gdrfgdrf.ConnectComputerServer.Annotation;

import org.springframework.web.bind.annotation.Mapping;

import java.lang.annotation.*;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Mapping
@Documented
public @interface SecurityParameter {
    boolean parameterDecode() default true;
    boolean responseEncode() default true;
}

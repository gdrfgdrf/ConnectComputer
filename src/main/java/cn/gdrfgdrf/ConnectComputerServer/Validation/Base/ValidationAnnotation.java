package cn.gdrfgdrf.ConnectComputerServer.Validation.Base;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;

/**
 * @author gdrfgdrf
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ANNOTATION_TYPE})
public @interface ValidationAnnotation {
}

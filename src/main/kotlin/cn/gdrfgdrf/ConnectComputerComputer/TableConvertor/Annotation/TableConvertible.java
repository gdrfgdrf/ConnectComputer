package cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Annotation;

import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Translator.Impl.DefaultTranslator;
import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.Translator.Translator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TableConvertible {
    Class<? extends Translator<?>> translator() default DefaultTranslator.class;
}

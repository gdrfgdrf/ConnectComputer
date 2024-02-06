package cn.gdrfgdrf.ConnectComputerServer.Validation.Annotation;

import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Base.DefaultMessage;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Base.ValidationAnnotation;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Validator.PatternAndMessageValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Documented;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author gdrfgdrf
 */
@ValidationAnnotation
@DefaultMessage(
        messageEnum = MessageEnum.VALIDATION_PATTERN
)
@Documented
@Constraint(validatedBy = { PatternAndMessageValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(PatternAndMessage.List.class)
public @interface PatternAndMessage {
    String message() default "";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    String regexp();

    MessageEnum overrideMessageEnum() default MessageEnum.UNKNOWN_LANGUAGE;

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        PatternAndMessage[] value();
    }
}

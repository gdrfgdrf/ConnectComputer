package cn.gdrfgdrf.ConnectComputerServer.Validation.Annotation;

import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Base.DefaultMessage;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Base.ValidationAnnotation;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Validator.NotBlankAndMessageValidator;
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
        messageEnum = MessageEnum.VALIDATION_NOT_BLANK
)
@Documented
@Constraint(validatedBy = { NotBlankAndMessageValidator.class })
@Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
@Retention(RUNTIME)
@Repeatable(NotBlankAndMessage.List.class)
public @interface NotBlankAndMessage {
    String message() default "";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };

    MessageEnum overrideMessageEnum() default MessageEnum.UNKNOWN_LANGUAGE;

    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    @interface List {
        NotBlankAndMessage[] value();
    }
}

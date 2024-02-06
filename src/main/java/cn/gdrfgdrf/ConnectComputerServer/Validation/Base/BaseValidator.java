package cn.gdrfgdrf.ConnectComputerServer.Validation.Base;

import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import cn.gdrfgdrf.ConnectComputerServer.Utils.StringUtils;
import jakarta.validation.ConstraintValidator;
import lombok.Getter;
import org.springframework.util.Assert;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
public abstract class BaseValidator<A extends Annotation, T> implements ConstraintValidator<A, T> {
    @Getter
    private static final Map<Class<? extends Annotation>, Method> CLASS_METHOD_MAP = new HashMap<>();

    protected MessageEnum messageEnum;

    @Override
    public void initialize(A constraintAnnotation) {
        if (constraintAnnotation.annotationType().isAnnotationPresent(DefaultMessage.class)) {
            DefaultMessage defaultMessage = constraintAnnotation.annotationType().getAnnotation(DefaultMessage.class);
            messageEnum = defaultMessage.messageEnum();
        }

        Method method = null;

        if (CLASS_METHOD_MAP.containsKey(constraintAnnotation.getClass())) {
            method = CLASS_METHOD_MAP.get(constraintAnnotation.getClass());
        } else {
            try {
                Class<? extends Annotation> clazz = constraintAnnotation.getClass();
                method = clazz.getMethod("overrideMessageEnum");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        Assert.notNull(method, "[Assertion failed] - this argument is required; it must not be null");

        try {
            MessageEnum result = (MessageEnum) method.invoke(constraintAnnotation);

            if (result != MessageEnum.UNKNOWN_LANGUAGE && !StringUtils.isBlank(result.toString())) {
                messageEnum = result;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

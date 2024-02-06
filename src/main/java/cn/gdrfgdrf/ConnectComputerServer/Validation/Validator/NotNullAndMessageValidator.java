package cn.gdrfgdrf.ConnectComputerServer.Validation.Validator;

import cn.gdrfgdrf.ConnectComputerServer.Validation.Annotation.NotNullAndMessage;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Base.BaseValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author gdrfgdrf
 */
public class NotNullAndMessageValidator extends BaseValidator<NotNullAndMessage, Object> {
    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                messageEnum.toString()
        ).addConstraintViolation();

        return value != null;
    }
}

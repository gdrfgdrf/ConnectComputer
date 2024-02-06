package cn.gdrfgdrf.ConnectComputerServer.Validation.Validator;

import cn.gdrfgdrf.ConnectComputerServer.Utils.StringUtils;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Annotation.NotBlankAndMessage;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Base.BaseValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author gdrfgdrf
 */
public class NotBlankAndMessageValidator extends BaseValidator<NotBlankAndMessage, String> {
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                messageEnum.toString()
        ).addConstraintViolation();

        return !StringUtils.isBlank(value);
    }
}

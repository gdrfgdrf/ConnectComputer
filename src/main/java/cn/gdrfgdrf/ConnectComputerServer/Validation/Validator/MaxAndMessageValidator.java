package cn.gdrfgdrf.ConnectComputerServer.Validation.Validator;

import cn.gdrfgdrf.ConnectComputerServer.Utils.StringUtils;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Annotation.MaxAndMessage;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Base.BaseValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author gdrfgdrf
 */
public class MaxAndMessageValidator extends BaseValidator<MaxAndMessage, String> {
    private int limit;

    @Override
    public void initialize(MaxAndMessage constraintAnnotation) {
        super.initialize(constraintAnnotation);
        limit = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                messageEnum.toString()
                        .replace("%MAX%", String.valueOf(limit))
        ).addConstraintViolation();

        return StringUtils.isBlank(value) || value.length() <= limit;
    }
}

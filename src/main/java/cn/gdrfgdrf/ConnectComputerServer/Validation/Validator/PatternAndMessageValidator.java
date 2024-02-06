package cn.gdrfgdrf.ConnectComputerServer.Validation.Validator;

import cn.gdrfgdrf.ConnectComputerServer.Utils.StringUtils;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Annotation.PatternAndMessage;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Base.BaseValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author gdrfgdrf
 */
public class PatternAndMessageValidator extends BaseValidator<PatternAndMessage, String> {
    private String regexp;

    @Override
    public void initialize(PatternAndMessage constraintAnnotation) {
        super.initialize(constraintAnnotation);
        regexp = constraintAnnotation.regexp();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                messageEnum.toString().replace("%PATTERN%", regexp)
        ).addConstraintViolation();

        return !StringUtils.isBlank(value) && StringUtils.verifyByPattern(value, regexp);
    }
}

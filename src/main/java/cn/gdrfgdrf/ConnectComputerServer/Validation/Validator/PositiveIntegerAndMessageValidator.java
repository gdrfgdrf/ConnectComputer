package cn.gdrfgdrf.ConnectComputerServer.Validation.Validator;

import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Annotation.PositiveIntegerAndMessage;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Base.BaseValidator;
import jakarta.validation.ConstraintValidatorContext;

/**
 * @author gdrfgdrf
 */
public class PositiveIntegerAndMessageValidator extends BaseValidator<PositiveIntegerAndMessage, Integer> {
    private boolean reverse;

    @Override
    public void initialize(PositiveIntegerAndMessage constraintAnnotation) {
        super.initialize(constraintAnnotation);
        reverse = constraintAnnotation.reverse();
    }

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(
                messageEnum.toString().replace("%POSITIVE_OR_NEGATIVE%",
                        reverse ? MessageEnum.NEGATIVE.toString() : MessageEnum.POSITIVE.toString())
        ).addConstraintViolation();

        return value != null && (reverse == (value < 0));
    }
}

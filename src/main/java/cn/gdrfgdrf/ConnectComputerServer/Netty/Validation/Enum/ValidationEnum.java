package cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Enum;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Base.Validator;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Validator.IntegerIsPositiveValidator;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Validator.NotNullValidator;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Validator.StringNotBlankValidator;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Validator.StringLengthNotOutOfLimited;
import lombok.Getter;

/**
 * @author gdrfgdrf
 */

public enum ValidationEnum {
    STRING_NOT_BLANK(new StringNotBlankValidator()),
    STRING_LENGTH_NOT_OUT_OF_LIMITED(new StringLengthNotOutOfLimited()),
    INTEGER_IS_POSITIVE(new IntegerIsPositiveValidator()),
    NOT_NULL(new NotNullValidator());

    @Getter
    private final Validator<?> validator;

    ValidationEnum(Validator<?> validator) {
        this.validator = validator;
    }
}

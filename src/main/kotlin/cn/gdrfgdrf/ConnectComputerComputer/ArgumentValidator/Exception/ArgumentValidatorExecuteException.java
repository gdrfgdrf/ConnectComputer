package cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Exception;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;

/**
 * @author gdrfgdrf
 */
public class ArgumentValidatorExecuteException extends CustomException {
    public ArgumentValidatorExecuteException() {
    }

    public ArgumentValidatorExecuteException(String message) {
        super(message);
    }

    public ArgumentValidatorExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentValidatorExecuteException(Throwable cause) {
        super(cause);
    }

    public ArgumentValidatorExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getErrorMessage() {
        return AppLocale.CUSTOM_EXCEPTION_ARGUMENT_VALIDATOR_EXECUTE;
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Exception;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;

/**
 * @author gdrfgdrf
 */
public class ArgumentAssignorExecuteException extends CustomException {
    public ArgumentAssignorExecuteException() {
    }

    public ArgumentAssignorExecuteException(String message) {
        super(message);
    }

    public ArgumentAssignorExecuteException(String message, Throwable cause) {
        super(message, cause);
    }

    public ArgumentAssignorExecuteException(Throwable cause) {
        super(cause);
    }

    public ArgumentAssignorExecuteException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getErrorMessage() {
        return AppLocale.CUSTOM_EXCEPTION_ARGUMENT_ASSIGNOR_EXECUTE;
    }
}

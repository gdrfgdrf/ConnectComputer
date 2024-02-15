package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Exception;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;

/**
 * @author gdrfgdrf
 */
public class ExceptionDispatchException extends CustomException {
    public ExceptionDispatchException() {
    }

    public ExceptionDispatchException(String message) {
        super(message);
    }

    public ExceptionDispatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionDispatchException(Throwable cause) {
        super(cause);
    }

    public ExceptionDispatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getErrorMessage() {
        return AppLocale.CUSTOM_EXCEPTION_EXCEPTION_DISPATCH_ERROR;
    }
}

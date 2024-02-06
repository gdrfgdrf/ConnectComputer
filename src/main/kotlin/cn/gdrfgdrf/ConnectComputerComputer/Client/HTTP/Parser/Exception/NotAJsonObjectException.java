package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.Exception;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;

/**
 * @author gdrfgdrf
 */
public class NotAJsonObjectException extends CustomException {
    public NotAJsonObjectException() {
    }

    public NotAJsonObjectException(String message) {
        super(message);
    }

    public NotAJsonObjectException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAJsonObjectException(Throwable cause) {
        super(cause);
    }

    public NotAJsonObjectException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getErrorMessage() {
        return AppLocale.CUSTOM_EXCEPTION_NOT_A_JSON_OBJECT;
    }
}

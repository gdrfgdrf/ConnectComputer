package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.Exception;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;

/**
 * @author gdrfgdrf
 */
public class JsonNullException extends CustomException {
    public JsonNullException() {
    }

    public JsonNullException(String message) {
        super(message);
    }

    public JsonNullException(String message, Throwable cause) {
        super(message, cause);
    }

    public JsonNullException(Throwable cause) {
        super(cause);
    }

    public JsonNullException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getErrorMessage() {
        return AppLocale.CUSTOM_EXCEPTION_JSON_NULL;
    }
}

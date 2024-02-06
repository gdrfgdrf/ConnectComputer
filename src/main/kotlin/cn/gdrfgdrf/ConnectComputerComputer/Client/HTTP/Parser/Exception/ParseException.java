package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.Exception;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;

/**
 * @author gdrfgdrf
 */
public class ParseException extends CustomException {
    public ParseException() {
    }

    public ParseException(String message) {
        super(message);
    }

    public ParseException(String message, Throwable cause) {
        super(message, cause);
    }

    public ParseException(Throwable cause) {
        super(cause);
    }

    public ParseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public String getErrorMessage() {
        return AppLocale.CUSTOM_EXCEPTION_PARSE;
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Handler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.Exception.JsonNullException;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.Exception.NotAJsonObjectException;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.Exception.ParseException;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Handler.ExceptionHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
@ExceptionHandlerInfo(
        classes = {
                ParseException.class
        }
)
public class ParseExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Throwable e, Object args) {
        e.printStackTrace();

        ParseException parseException = (ParseException) e;
        Exception cause = (Exception) parseException.getCause();

        String message = null;
        if (cause instanceof JsonNullException jsonNullException) {
            message = jsonNullException.getErrorMessage();
            return;
        }
        if (cause instanceof NotAJsonObjectException notAJsonObjectException) {
            message = notAJsonObjectException.getErrorMessage();
            return;
        }
        if (StringUtils.isBlank(message)) {
            message = parseException.getErrorMessage();
        }

        log.error(message);
    }
}

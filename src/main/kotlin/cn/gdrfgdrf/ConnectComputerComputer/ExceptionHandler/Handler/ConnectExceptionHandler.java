package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Handler;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Handler.ExceptionHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import lombok.extern.slf4j.Slf4j;

import java.net.ConnectException;

/**
 * @author gdrfgdrf
 */
@Slf4j
@ExceptionHandlerInfo(
        classes = {
                ConnectException.class
        }
)
public class ConnectExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Throwable e, Object args) {
        log.error(AppLocale.CONNECT_FAILED_ERROR);
    }
}

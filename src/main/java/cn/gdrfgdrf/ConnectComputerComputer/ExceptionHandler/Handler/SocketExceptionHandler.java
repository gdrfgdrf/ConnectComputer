package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Handler;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Handler.ExceptionHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketException;

/**
 * @author gdrfgdrf
 */
@Slf4j
@ExceptionHandlerInfo(
        classes = {
                SocketException.class
        }
)
public class SocketExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Throwable e, Object args) {
        log.error(AppLocale.SOCKET_CHANNEL_ERROR, e.getMessage());
    }
}

package cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Manager;

import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Annotation.ExceptionHandlerClass;
import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Handler.ExceptionHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Handler.SocketExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * @author gdrfgdrf
 */
@Order(2)
@Component
@ExceptionHandlerClass(classes = {
        SocketExceptionHandler.class,
        ExceptionHandler.class
})
public class ExceptionHandlerManager extends AbstractExceptionHandlerManager {
    public ExceptionHandlerManager() {
        super.initExceptionHandler();
    }
}

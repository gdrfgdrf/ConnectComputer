package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Handler;

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Exception.NettyNotFoundPacketHandlerException;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
@ExceptionHandlerInfo(
        classes = {
                NettyNotFoundPacketHandlerException.class
        }
)
public class NotFoundPacketHandlerExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Throwable e, Object args) {
        NettyNotFoundPacketHandlerException exception = (NettyNotFoundPacketHandlerException) e;

        log.error(
                exception.getErrorMessage(),
                exception.getMsg().getClass().getSimpleName()
        );
    }
}

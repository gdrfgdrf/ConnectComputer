package cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Handler;

import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Base.ExceptionHandler;
import io.netty.channel.Channel;
import lombok.extern.slf4j.Slf4j;

import java.net.SocketException;

/**
 * @author gdrfgdrf
 */
@ExceptionHandlerInfo(classes = {
        SocketException.class
})
@Slf4j
public class SocketExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Channel channel, Throwable e) {
        log.warn("SocketException from {}: {}", channel.remoteAddress(), e);
    }
}

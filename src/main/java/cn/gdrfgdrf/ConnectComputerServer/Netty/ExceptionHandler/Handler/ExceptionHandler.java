package cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Handler;

import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
@ExceptionHandlerInfo(
        classes = {
                Exception.class
        }
)
public class ExceptionHandler implements cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Base.ExceptionHandler {
    @Override
    public void handle(Channel channel, Throwable e) {
        e.printStackTrace();
    }
}

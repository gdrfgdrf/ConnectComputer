package cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Base;

import io.netty.channel.Channel;

/**
 * @author gdrfgdrf
 */
public interface ExceptionHandler {
    void handle(Channel channel, Throwable e);
}

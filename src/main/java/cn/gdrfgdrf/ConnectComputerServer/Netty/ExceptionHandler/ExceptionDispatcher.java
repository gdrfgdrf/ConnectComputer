package cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler;

import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Base.ExceptionHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Exception.ExceptionDispatchException;
import io.netty.channel.Channel;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@Order(1)
@Component
public class ExceptionDispatcher {
    public final Map<Class<? extends Exception>, List<ExceptionHandler>> HANDLER = new HashMap<>();

    public void registerExceptionHandler(Class<? extends Exception> type, ExceptionHandler handler) {
        HANDLER.computeIfAbsent(type, k -> new LinkedList<>());
        HANDLER.get(type).add(handler);
    }

    public void dispatch(Channel channel, Throwable throwable) throws ExceptionDispatchException {
        if (throwable == null) {
            throw new ExceptionDispatchException("The exception is null");
        }
        if (throwable.getClass() == ExceptionDispatchException.class) {
            return;
        }

        List<ExceptionHandler> handler = HANDLER.get(throwable.getClass());
        if (handler == null || handler.isEmpty()) {
            handler = HANDLER.get(Exception.class);

            if (handler == null) {
                throwable.printStackTrace();
                throw new ExceptionDispatchException("Not found the exception handler with " +
                        "the exception type is " + throwable.getClass());
            }
        }

        handler.forEach(exceptionHandler -> {
            exceptionHandler.handle(channel, throwable);
        });
    }

}

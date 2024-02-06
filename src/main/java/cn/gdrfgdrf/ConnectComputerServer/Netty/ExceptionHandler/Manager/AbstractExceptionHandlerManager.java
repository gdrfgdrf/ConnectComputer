package cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Manager;

import cn.gdrfgdrf.ConnectComputerServer.Holder.SpringContextHolder;
import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Annotation.ExceptionHandlerClass;
import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Base.ExceptionHandler;
import cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.ExceptionDispatcher;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractExceptionHandlerManager {
    private final ExceptionDispatcher exceptionDispatcher = SpringContextHolder.getBean(ExceptionDispatcher.class);

    protected void initExceptionHandler() {
        initAnnotationExceptionHandler();
    }

    private void initAnnotationExceptionHandler() {
        ExceptionHandlerClass exceptionHandlerClass = getClass().getAnnotation(ExceptionHandlerClass.class);
        if (exceptionHandlerClass == null) {
            return;
        }

        Class<? extends ExceptionHandler>[] classes = exceptionHandlerClass.classes();
        for (Class<? extends ExceptionHandler> clazz : classes) {
            initExceptionHandler(clazz);
        }
    }

    private void initExceptionHandler(Class<? extends ExceptionHandler> clazz) {
        try {
            ExceptionHandlerInfo exceptionHandlerInfo = clazz.getAnnotation(ExceptionHandlerInfo.class);
            Assert.notNull(exceptionHandlerInfo, "ExceptionHandlerInfo is not found in " + clazz.getSimpleName());

            ExceptionHandler exceptionHandler = clazz.getDeclaredConstructor().newInstance();

            for (Class<? extends Exception> type : exceptionHandlerInfo.classes()) {
                exceptionDispatcher.registerExceptionHandler(type, exceptionHandler);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

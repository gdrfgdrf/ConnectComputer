package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerClass;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionPostProcessorClass;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Handler.ExceptionHandler;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.ExceptionDispatcher;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Processor.ExceptionPostProcessor;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractExceptionManager {
    protected void initExceptionHandler() {
        initAnnotationExceptionPostProcessor();
        initAnnotationExceptionHandler();
    }

    private void initAnnotationExceptionPostProcessor() {
        ExceptionPostProcessorClass exceptionPostProcessorClass = getClass().getAnnotation(ExceptionPostProcessorClass.class);
        if (exceptionPostProcessorClass == null) {
            return;
        }

        Class<? extends ExceptionPostProcessor>[] classes = exceptionPostProcessorClass.classes();
        for (Class<? extends ExceptionPostProcessor> clazz : classes) {
            initExceptionPostProcessor(clazz);
        }
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

    private void initExceptionPostProcessor(Class<? extends ExceptionPostProcessor> clazz) {
        try {
            ExceptionPostProcessor processor = clazz.getDeclaredConstructor().newInstance();
            Class<? extends Exception> T = (Class<? extends Exception>) ClassUtils.getInterfaceT(processor, 0);

            ExceptionDispatcher.INSTANCE.registerExceptionPostProcessor(T, processor);

            log.info(
                    "Register exception post processor {} for exception {}",
                    clazz.getSimpleName(),
                    T.getSimpleName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void initExceptionHandler(Class<? extends ExceptionHandler> clazz) {
        try {
            ExceptionHandlerInfo exceptionHandlerInfo = clazz.getAnnotation(ExceptionHandlerInfo.class);
            assert exceptionHandlerInfo != null;

            ExceptionHandler handler = clazz.getDeclaredConstructor().newInstance();

            for (Class<? extends Exception> type : exceptionHandlerInfo.classes()) {
                ExceptionDispatcher.INSTANCE.registerExceptionHandler(type, handler);

                log.info(
                        "Register exception handler {} for exception {}",
                        clazz.getSimpleName(),
                        type.getSimpleName()
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

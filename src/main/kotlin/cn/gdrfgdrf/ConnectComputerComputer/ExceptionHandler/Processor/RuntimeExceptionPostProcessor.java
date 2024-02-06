package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Processor;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Processor.ExceptionPostProcessor;

/**
 * @author gdrfgdrf
 */
public class RuntimeExceptionPostProcessor implements ExceptionPostProcessor<RuntimeException> {
    @Override
    public Exception postProcessorBeforeHandler(RuntimeException e) {
        Exception result = e;

        if (e != null) {
            result = (Exception) e.getCause();
        }

        return result;
    }
}

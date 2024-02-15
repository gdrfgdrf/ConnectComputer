package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerClass;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionPostProcessorClass;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Handler.*;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Processor.RuntimeExceptionPostProcessor;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;

/**
 * @author gdrfgdrf
 */
@ExceptionPostProcessorClass(
        classes = {
                RuntimeExceptionPostProcessor.class
        }
)
@ExceptionHandlerClass(
        classes = {
                ExceptionHandler.class,
                ParseExceptionHandler.class,
                ConnectExceptionHandler.class,
                ArgumentExceptionHandler.class,
                SSLExceptionHandler.class,

                SocketExceptionHandler.class,
                NotFoundPacketHandlerExceptionHandler.class
        }
)
public class ExceptionManager extends AbstractExceptionManager implements Bean {
    @Override
    public void init() throws Exception {
        super.initExceptionHandler();
    }
}

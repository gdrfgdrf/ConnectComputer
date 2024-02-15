package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Handler;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Exception.ArgumentAssignorExecuteException;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Exception.ArgumentValidatorExecuteException;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Handler.ExceptionHandler;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
@ExceptionHandlerInfo(
        classes = {
                ArgumentAssignorExecuteException.class,
                ArgumentValidatorExecuteException.class
        }
)
public class ArgumentExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Throwable e, Object args) {
        CustomException customException = (CustomException) e;
        log.error(
                customException.getErrorMessage() + ": {}",
                customException.getMessage()
        );
    }
}

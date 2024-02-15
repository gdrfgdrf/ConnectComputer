package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Handler;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
@ExceptionHandlerInfo(
        classes = {
                Exception.class
        }
)
public class ExceptionHandler implements cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Handler.ExceptionHandler {
    @Override
    public void handle(Throwable e, Object args) {
        if (e instanceof CustomException customException) {
            log.error(customException.getErrorMessage());
            return;
        }

        if (args instanceof String str && !StringUtils.isBlank(str)) {
            e.printStackTrace();
            log.error(str);
            return;
        }
        e.printStackTrace();
        log.error(AppLocale.UNKNOWN_ERROR);
    }
}

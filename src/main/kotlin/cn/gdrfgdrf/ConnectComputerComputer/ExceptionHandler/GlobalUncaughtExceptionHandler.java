package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler;

import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class GlobalUncaughtExceptionHandler implements BetterUncaughtExceptionHandler, Bean {
    @Override
    public void init() throws Exception {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    @Override
    public void uncaughtException(Thread t, Throwable throwable, Object args) {
        if (throwable instanceof StackOverflowError) {
            log.error("StackOverflowError");
            return;
        }

        try {
            ExceptionDispatcher.INSTANCE.dispatch(throwable, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

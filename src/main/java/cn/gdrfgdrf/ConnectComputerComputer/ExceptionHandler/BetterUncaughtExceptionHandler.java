package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler;

/**
 * @author gdrfgdrf
 */
public interface BetterUncaughtExceptionHandler extends Thread.UncaughtExceptionHandler {
    @Override
    default void uncaughtException(Thread t, Throwable e) {
        uncaughtException(t, e, null);
    };

    void uncaughtException(Thread t,Throwable e, Object args);
}

package cn.gdrfgdrf.ConnectComputerServer.Netty.ExceptionHandler.Exception;

/**
 * @author gdrfgdrf
 */
public class ExceptionDispatchException extends Exception {
    public ExceptionDispatchException() {
    }

    public ExceptionDispatchException(String message) {
        super(message);
    }

    public ExceptionDispatchException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExceptionDispatchException(Throwable cause) {
        super(cause);
    }

    public ExceptionDispatchException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

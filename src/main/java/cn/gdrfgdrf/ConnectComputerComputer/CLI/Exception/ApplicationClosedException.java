package cn.gdrfgdrf.ConnectComputerComputer.CLI.Exception;

/**
 * @author gdrfgdrf
 */
public class ApplicationClosedException extends Exception {
    public ApplicationClosedException() {
    }

    public ApplicationClosedException(String message) {
        super(message);
    }

    public ApplicationClosedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ApplicationClosedException(Throwable cause) {
        super(cause);
    }

    public ApplicationClosedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

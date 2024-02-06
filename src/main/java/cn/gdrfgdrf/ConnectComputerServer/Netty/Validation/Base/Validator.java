package cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Base;

/**
 * @author gdrfgdrf
 */
public interface Validator<T> {
    boolean validate(T argument, Object args);
}

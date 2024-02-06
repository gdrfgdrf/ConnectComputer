package cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Base;

/**
 * @author gdrfgdrf
 */
public interface Validator<T> {
    boolean validate(T argument);
    String getInvalidMessage();
}

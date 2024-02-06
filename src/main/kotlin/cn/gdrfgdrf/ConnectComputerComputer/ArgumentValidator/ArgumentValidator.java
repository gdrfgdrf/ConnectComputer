package cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator;

/**
 * @author gdrfgdrf
 */
public interface ArgumentValidator<T> {
    boolean validate(T argument);
    String getShownName();
    String getErrorMessage();
}

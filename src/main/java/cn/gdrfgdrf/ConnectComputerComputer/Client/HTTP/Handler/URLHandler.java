package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Handler;

/**
 * @author gdrfgdrf
 */
public interface URLHandler<T> {
    String handle(String url, T[] args);
}

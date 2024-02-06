package cn.gdrfgdrf.ConnectComputerComputer.Menu;

import cn.gdrfgdrf.ConnectComputerComputer.Menu.Network.Http.Base.MenuHttpNetworkRequest;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gdrfgdrf
 */
public abstract class Menu {
    @Setter
    @Getter
    private String route;
    @Setter
    @Getter
    private boolean popupFinished = false;
    @Setter
    @Getter
    private Object popupOnDismissArgument;
    @Getter
    private final Object lock = new Object();

    public abstract String getTitle();
    public abstract void popup(Object args, MenuHttpNetworkRequest request) throws Exception;
    public abstract void dismiss() throws Exception;
    public abstract void userInput(String input) throws Exception;
}

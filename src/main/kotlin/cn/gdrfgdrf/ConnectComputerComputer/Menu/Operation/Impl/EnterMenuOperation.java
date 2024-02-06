package cn.gdrfgdrf.ConnectComputerComputer.Menu.Operation.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.Menu.MenuNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Operation.Base.Operation;

/**
 * @author gdrfgdrf
 */
public class EnterMenuOperation implements Operation {
    private final String menuRoute;

    public EnterMenuOperation(String menuRoute) {
        this.menuRoute = menuRoute;
    }

    @Override
    public void execute(Object args) {
        MenuNavigator.INSTANCE.popup(menuRoute, args);
    }

    @Override
    public String getShownName() {
        return MenuNavigator.INSTANCE.getMenu(menuRoute).getTitle();
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.Menu.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Global.MenuRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Annotation.MenuClass;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Impl.ComputerControlMenu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Impl.ComputerDetailMenu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Impl.ComputerListMenu;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;

/**
 * @author gdrfgdrf
 */
@MenuClass(classes = {
        ComputerListMenu.class,
        ComputerDetailMenu.class,
        ComputerControlMenu.class
},
        routes = {
                MenuRoute.MENU_ROUTE_COMPUTER_LIST,
                MenuRoute.MENU_ROUTE_COMPUTER_DETAIL,
                MenuRoute.MENU_ROUTE_COMPUTER_CONTROL
        })
public class MenuManager extends AbstractMenuManager implements Bean {
    @Override
    public void run() throws Exception {
        super.initMenu();
    }
}

/*
 * Copyright (C) 2024 Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.gdrfgdrf.ConnectComputerComputer.Menu.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Global.Route.MenuRoute;
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
    public void init() throws Exception {
        super.initMenu();
    }
}

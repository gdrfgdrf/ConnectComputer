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

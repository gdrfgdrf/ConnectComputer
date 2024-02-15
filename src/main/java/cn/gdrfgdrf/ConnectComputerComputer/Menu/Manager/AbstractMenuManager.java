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

import cn.gdrfgdrf.ConnectComputerComputer.Interceptor.MenuMethodInterceptor;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Annotation.MenuClass;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Menu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.MenuNavigator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractMenuManager {
    protected void initMenu() {
        initAnnotationMenu();
    }

    private void initAnnotationMenu() {
        MenuClass menuClass = getClass().getAnnotation(MenuClass.class);
        if (menuClass == null) {
            return;
        }

        Class<? extends Menu>[] classes = menuClass.classes();
        String[] routes = menuClass.routes();

        for (int i = 0; i < classes.length; i++) {
            String route = routes[i];
            Class<? extends Menu> clazz = classes[i];

            initMenu(route, clazz);
        }
    }

    private void initMenu(String route, Class<? extends Menu> clazz) {
        try {
            Menu menu = MenuMethodInterceptor.INSTANCE.createInstance(clazz);

            MenuNavigator.INSTANCE.registerMenu(route, menu);

            log.info("Register menu {}", menu.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

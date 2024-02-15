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

package cn.gdrfgdrf.ConnectComputerComputer.Menu;

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
    public abstract void popup(Object args) throws Exception;
    public abstract void dismiss() throws Exception;
    public abstract void userInput(String input) throws Exception;
}

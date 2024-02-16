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

package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Handler;

import cn.gdrfgdrf.ConnectComputerComputer.Api.Exception.PluginLoadException;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.Handler.ExceptionHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@ExceptionHandlerInfo(
        classes = {
                PluginLoadException.class
        }
)
@Slf4j
public class PluginLoadExceptionHandler implements ExceptionHandler {
    @Override
    public void handle(Throwable e, Object args) {
        e.printStackTrace();

        PluginLoadException exception = (PluginLoadException) e;
        log.error(exception.getErrorMessage());
    }
}

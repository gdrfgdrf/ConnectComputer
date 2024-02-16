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

package cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Api.Exception.PluginLoadException;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionHandlerClass;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Annotation.ExceptionPostProcessorClass;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Handler.*;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Processor.RuntimeExceptionPostProcessor;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;

/**
 * @author gdrfgdrf
 */
@ExceptionPostProcessorClass(
        classes = {
                RuntimeExceptionPostProcessor.class
        }
)
@ExceptionHandlerClass(
        classes = {
                ExceptionHandler.class,
                ParseExceptionHandler.class,
                ConnectExceptionHandler.class,
                ArgumentExceptionHandler.class,
                SSLExceptionHandler.class,

                SocketExceptionHandler.class,
                NotFoundPacketHandlerExceptionHandler.class,

                PluginLoadExceptionHandler.class
        }
)
public class ExceptionManager extends AbstractExceptionManager implements Bean {
    @Override
    public void init() throws Exception {
        super.initExceptionHandler();
    }
}

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

package cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.Annotation.PluginValidatorClass;
import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.PluginValidator;
import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.PluginValidatorExecutor;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.ClassUtils;

/**
 * @author gdrfgdrf
 */
public abstract class AbstractPluginValidatorManager {
    protected void initPluginValidator() {
        initAnnotationPluginValidator();
    }

    private void initAnnotationPluginValidator() {
        PluginValidatorClass pluginValidatorClass = getClass().getAnnotation(PluginValidatorClass.class);
        if (pluginValidatorClass == null) {
            return;
        }

        Class<? extends PluginValidator<?>>[] classes = pluginValidatorClass.classes();
        for (Class<? extends PluginValidator<?>> clazz : classes) {
            initPluginValidator(clazz);
        }
    }

    private void initPluginValidator(Class<? extends PluginValidator<?>> clazz) {
        try {
            PluginValidator<?> pluginValidator = clazz.getDeclaredConstructor().newInstance();
            Class<?> T = ClassUtils.getInterfaceT(pluginValidator, 0);

            PluginValidatorExecutor.INSTANCE.registerPluginValidator(pluginValidator, T);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

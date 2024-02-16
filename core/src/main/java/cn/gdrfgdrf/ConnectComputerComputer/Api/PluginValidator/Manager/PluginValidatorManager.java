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
import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.PluginValidatorExecutor;
import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.Validator.NotBlankPluginValidator;
import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.Validator.VersionMaxPluginValidator;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;

/**
 * 加载 插件校验器
 * @author gdrfgdrf
 */
@PluginValidatorClass(classes = {
        NotBlankPluginValidator.class,
        VersionMaxPluginValidator.class
})
public class PluginValidatorManager extends AbstractPluginValidatorManager implements Bean {
    @Override
    public void init() throws Exception {
        super.initPluginValidator();

        PluginValidatorClass pluginValidatorClass = getClass().getAnnotation(PluginValidatorClass.class);
        if (pluginValidatorClass == null) {
            throw new IllegalStateException("Not found annotation PluginValidatorClass");
        }

        int needCount = pluginValidatorClass.classes().length;
        int realCount = PluginValidatorExecutor.INSTANCE.getPluginValidatorCount();

        if (needCount != realCount) {
            throw new IllegalStateException(
                    "The quantity of PluginValidator required is different from the actual quantity of PluginValidator"
            );
        }
    }
}

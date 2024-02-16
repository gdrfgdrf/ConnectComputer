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

package cn.gdrfgdrf.ConnectComputerComputer.Api.Base;

import cn.gdrfgdrf.ConnectComputerComputer.Api.Configuration.CoreConfig;
import cn.gdrfgdrf.ConnectComputerComputer.Api.Manager.PluginManager;

/**
 * 构造 {@link Plugin} 时需要的参数集合
 * @author gdrfgdrf
 */
public class PluginArgumentSet {
    private final CoreConfig coreConfig;
    private final PluginManager pluginManager;

    /**
     * 构造参数集合
     * @param coreConfig core 配置
     * @param pluginManager 插件管理器
     */
    public PluginArgumentSet(CoreConfig coreConfig, PluginManager pluginManager) {
        this.coreConfig = coreConfig;
        this.pluginManager = pluginManager;
    }

    /**
     * 获取 core 配置
     * @return core 配置
     */
    public CoreConfig getCoreConfig() {
        return coreConfig;
    }

    /**
     * 获取插件管理器
     * @return 插件管理器
     */
    public PluginManager getPluginManager() {
        return pluginManager;
    }
}

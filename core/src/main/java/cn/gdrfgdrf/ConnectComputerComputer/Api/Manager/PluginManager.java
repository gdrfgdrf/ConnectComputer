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

package cn.gdrfgdrf.ConnectComputerComputer.Api.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Api.Base.Plugin;
import lombok.extern.slf4j.Slf4j;

/**
 * 插件管理器
 * @author gdrfgdrf
 */
public interface PluginManager {
    PluginManager INSTANCE = new PluginManagerImpl();

    /**
     * 注册插件
     * @param plugin {@link Plugin} 实例
     */
    void registerPlugin(Plugin plugin);
    /**
     * 注销插件
     * @param plugin {@link Plugin} 实例
     */
    void unregisterPlugin(Plugin plugin);

    /**
     * 加载插件
     * @param plugin {@link Plugin} 实例
     */
    void loadPlugin(Plugin plugin);

    /**
     * 获取插件
     * @param pluginName 插件名
     * @return 插件信息
     */
    PluginInfo getPlugin(String pluginName);

    /**
     * 获取插件列表
     * @return 插件信息的集合
     */
    PluginInfo[] getPlugins();

    /**
     * 启用插件
     * @param plugin {@link Plugin} 实例
     */
    void enablePlugin(Plugin plugin);
    /**
     * 禁用插件
     * @param plugin {@link Plugin} 实例
     */
    void disablePlugin(Plugin plugin);

    /**
     * 某个插件是否启用
     * @param plugin {@link Plugin} 实例
     * @return 插件启用状态
     */
    boolean isPluginEnabled(Plugin plugin);
}

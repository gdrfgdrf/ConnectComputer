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
import cn.gdrfgdrf.ConnectComputerComputer.Api.Configuration.PluginConfig;

/**
 * 插件信息类，存储以下信息
 * 插件继承了 Plugin 的类的实例
 * 插件配置
 * 插件是否开启
 * 插件是否加载
 * @author gdrfgdrf
 */
public class PluginInfo {
    private final Plugin plugin;
    private final PluginConfig pluginConfig;
    private boolean enabled = false;
    private boolean loaded = false;

    /**
     * 构造插件信息
     * @param plugin {@link Plugin} 实例
     */
    public PluginInfo(Plugin plugin) {
        this.plugin = plugin;
        this.pluginConfig = plugin.getPluginConfig();
    }

    /**
     * 获取 {@link Plugin} 实例
     * @return {@link Plugin} 实例
     */
    public Plugin getPlugin() {
        return plugin;
    }

    /**
     * 获取插件配置
     * @return 插件配置
     */
    public PluginConfig getPluginConfig() {
        return pluginConfig;
    }

    /**
     * 插件是否启用
     * @return 插件是否启用
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * 设置插件是否启用
     * @param enabled 是否启用
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 插件是否加载成功
     * @return 插件是否加载成功
     */
    public boolean isLoaded() {
        return loaded;
    }

    /**
     * 设置插件是否加载成功
     * @param loaded 插件是否加载成功
     */
    public void setLoaded(boolean loaded) {
        this.loaded = loaded;
    }
}

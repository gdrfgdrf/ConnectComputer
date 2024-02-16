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
import cn.gdrfgdrf.ConnectComputerComputer.Api.Configuration.PluginConfig;
import cn.gdrfgdrf.ConnectComputerComputer.Api.Manager.PluginManager;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 插件基类，每一个插件都应该有一个该类的子类
 * @author gdrfgdrf
 */
public abstract class Plugin {
    private final PluginArgumentSet pluginArgumentSet;

    /**
     * 运行时由 core 调用，使用者无需调用
     * @param pluginArgumentSet 参数集合
     */
    public Plugin(PluginArgumentSet pluginArgumentSet) {
        this.pluginArgumentSet = pluginArgumentSet;
    }

    /**
     * 开始加载插件
     */
    public void onLoad() {};
    /**
     * 插件被启用
     */
    public void onEnable() {};
    /**
     * 插件被禁用
     */
    public void onDisable() {};

    /**
     * 获取插件自身配置
     * @return 插件自身配置
     */
    public abstract PluginConfig getPluginConfig();

    /**
     * 获取参数集合
     * @return 参数集合
     */
    public PluginArgumentSet getPluginArgumentSet() {
        return pluginArgumentSet;
    }
}

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
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 插件管理器实现类
 * @author gdrfgdrf
 */
@Slf4j
public class PluginManagerImpl implements PluginManager {
    private static final Map<String, PluginInfo> PLUGIN_MAP = new ConcurrentHashMap<>();

    @Override
    public void registerPlugin(Plugin plugin) {
        PluginInfo pluginInfo = new PluginInfo(plugin);
        PluginConfig pluginConfig = pluginInfo.getPluginConfig();
        String pluginName = pluginConfig.getName();

        PLUGIN_MAP.put(pluginName, pluginInfo);

        log.info("Registered plugin {}", pluginName);
    }

    @Override
    public void unregisterPlugin(Plugin plugin) {
        PluginInfo pluginInfo = new PluginInfo(plugin);
        PluginConfig pluginConfig = pluginInfo.getPluginConfig();
        String pluginName = pluginConfig.getName();

        disablePlugin(plugin);

        PLUGIN_MAP.remove(pluginName);
        log.info("Unregistered plugin {}", pluginName);
    }

    @Override
    public void loadPlugin(Plugin plugin) {
        PluginConfig pluginConfig = plugin.getPluginConfig();
        String pluginName = pluginConfig.getName();
        PluginInfo pluginInfo = getPlugin(pluginName);

        log.info("Loading {}", pluginName);
        plugin.onLoad();
        pluginInfo.setLoaded(true);
        log.info("{} was loaded successfully", pluginName);
    }

    @Override
    public PluginInfo getPlugin(String pluginName) {
        return PLUGIN_MAP.get(pluginName);
    }

    @Override
    public PluginInfo[] getPlugins() {
        return PLUGIN_MAP.values().toArray(PluginInfo[]::new);
    }

    @Override
    public void enablePlugin(Plugin plugin) {
        PluginConfig pluginConfig = plugin.getPluginConfig();
        String pluginName = pluginConfig.getName();
        PluginInfo pluginInfo = getPlugin(pluginName);
        if (pluginInfo.isEnabled()) {
            return;
        }

        log.info("Enabling {}", pluginName);
        plugin.onEnable();
        pluginInfo.setEnabled(true);
        log.info("{} was enabled", pluginName);
    }

    @Override
    public void disablePlugin(Plugin plugin) {
        PluginConfig pluginConfig = plugin.getPluginConfig();
        String pluginName = pluginConfig.getName();
        PluginInfo pluginInfo = getPlugin(pluginName);
        if (!pluginInfo.isEnabled()) {
            return;
        }

        log.info("Disabling {}", pluginName);
        plugin.onDisable();
        pluginInfo.setEnabled(false);
        pluginInfo.setLoaded(false);
        log.info("{} was disabled", pluginName);
    }

    @Override
    public boolean isPluginEnabled(Plugin plugin) {
        PluginInfo pluginInfo = getPlugin(plugin.getPluginConfig().getName());
        return pluginInfo.isEnabled();
    }
}

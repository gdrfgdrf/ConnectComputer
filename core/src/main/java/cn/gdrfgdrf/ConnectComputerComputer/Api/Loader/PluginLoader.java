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

package cn.gdrfgdrf.ConnectComputerComputer.Api.Loader;

import cn.gdrfgdrf.ConnectComputerComputer.Api.Base.PluginArgumentSet;
import cn.gdrfgdrf.ConnectComputerComputer.Api.Configuration.CoreConfig;
import cn.gdrfgdrf.ConnectComputerComputer.Api.Configuration.CoreConfigImpl;
import cn.gdrfgdrf.ConnectComputerComputer.Api.Exception.PluginLoadException;
import cn.gdrfgdrf.ConnectComputerComputer.Api.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Api.Information.PluginInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Api.Manager.PluginManager;
import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.PluginValidatorExecutor;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.FileUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.JacksonUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Api.Base.Plugin;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Arrays;
import java.util.Objects;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 插件加载器
 * @author gdrfgdrf
 */
@Slf4j
public class PluginLoader implements Bean {
    private PluginArgumentSet pluginArgumentSet;

    @Override
    public void init() throws Exception {
        pluginArgumentSet = new PluginArgumentSet(
                CoreConfig.INSTANCE,
                PluginManager.INSTANCE
        );
        load();
    }

    private void load() throws PluginLoadException {
        if (pluginArgumentSet == null) {
            throw new PluginLoadException(new IllegalArgumentException("PluginArgumentSet cannot be null"));
        }

        File[] plugins = getPluginFiles();

        for (File pluginFile : plugins) {
            try {
                JarFile jarFile = new JarFile(pluginFile);
                JarEntry jarEntry = jarFile.getJarEntry("plugin.json");
                if (jarEntry == null) {
                    throw new PluginLoadException(new FileNotFoundException("plugin.json not found in Jar"));
                }

                InputStream inputStream = jarFile.getInputStream(jarEntry);
                PluginInformation pluginInformation = JacksonUtils.readInputStream(
                        inputStream,
                        PluginInformation.class
                );
                PluginValidatorExecutor.INSTANCE.validate(pluginInformation);

                URLClassLoader urlClassLoader =  new URLClassLoader(new URL[]{pluginFile.toURI().toURL()});
                Class<?> jarClass = urlClassLoader.loadClass(pluginInformation.getPluginClass());
                Class<? extends Plugin> pluginClass = jarClass.asSubclass(Plugin.class);

                Plugin pluginInstance = pluginClass.getDeclaredConstructor(PluginArgumentSet.class)
                        .newInstance(pluginArgumentSet);
                PluginManager.INSTANCE.registerPlugin(pluginInstance);
                PluginManager.INSTANCE.enablePlugin(pluginInstance);
                PluginManager.INSTANCE.loadPlugin(pluginInstance);
            } catch (Exception e) {
                Thread.getDefaultUncaughtExceptionHandler().uncaughtException(
                        Thread.currentThread(),
                        new PluginLoadException(e)
                );
            }
        }
    }

    private File[] getPluginFiles() {
        File pluginFolder = new File(Constants.PLUGIN_FOLDER);
        if (!pluginFolder.exists()) {
            pluginFolder.mkdirs();
        }

        return Arrays.stream(Objects.requireNonNull(pluginFolder.listFiles(file -> {
            String extension = FileUtils.getExtension(file);
            return !"jar".equals(extension);
        }))).toArray(File[]::new);
    }
}

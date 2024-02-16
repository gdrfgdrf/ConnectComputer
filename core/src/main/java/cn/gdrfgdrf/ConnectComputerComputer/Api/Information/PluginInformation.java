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

package cn.gdrfgdrf.ConnectComputerComputer.Api.Information;

import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.Annotation.NotBlank;
import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.Annotation.VersionMax;
import lombok.Data;

/**
 * 插件 Jar 包中 plugin.json 的反序列化对象
 * @author gdrfgdrf
 */
@Data
public class PluginInformation {
    /**
     * 继承了 {@link cn.gdrfgdrf.ConnectComputerComputer.Api.Base.Plugin} 的类的全限定名
     */
    @NotBlank
    private String pluginClass;
    /**
     * 开发插件时使用的 core 中 {@link cn.gdrfgdrf.ConnectComputerComputer.Api.Enum.VersionEnum#CURRENT} 的值
     */
    @NotBlank
    @VersionMax
    private String coreVersion;
}

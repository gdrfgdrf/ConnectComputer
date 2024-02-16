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

package cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.Validator;

import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.Annotation.NotBlank;
import cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator.PluginValidator;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;

import java.lang.annotation.Annotation;

/**
 * @author gdrfgdrf
 */
public class NotBlankPluginValidator implements PluginValidator<String> {
    @Override
    public void validate(String s, String fieldName) {
        if (StringUtils.isBlank(s)) {
            throw new IllegalArgumentException(fieldName + " is blank");
        }
    }

    @Override
    public Class<? extends Annotation> getAnnotation() {
        return NotBlank.class;
    }
}

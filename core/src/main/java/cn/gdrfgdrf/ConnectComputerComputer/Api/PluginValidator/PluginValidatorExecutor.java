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

package cn.gdrfgdrf.ConnectComputerComputer.Api.PluginValidator;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * 校验执行器
 * @author gdrfgdrf
 */
public enum PluginValidatorExecutor {
    INSTANCE;

    private final Map<Class<? extends Annotation>, PluginValidator<?>> PLUGIN_VALIDATOR = new HashMap<>();
    private final Map<PluginValidator<?>, Class<?>> T_MAP = new HashMap<>();

    public void registerPluginValidator(PluginValidator<?> pluginValidator, Class<?> T) {
        PLUGIN_VALIDATOR.put(pluginValidator.getAnnotation(), pluginValidator);
        T_MAP.put(pluginValidator, T);
    }

    public void validate(Object obj) throws Exception {
        Field[] fields = Arrays.stream(obj.getClass().getDeclaredFields())
                .filter(field -> field.getAnnotations().length != 0)
                .filter(field -> {
                    Annotation[] annotations = field.getAnnotations();
                    for (Annotation annotation : annotations) {
                        Class<?> annotationClass = annotation.getClass();
                        if (!PLUGIN_VALIDATOR.containsKey(annotationClass)) {
                            continue;
                        }

                        PluginValidator<?> pluginValidator = PLUGIN_VALIDATOR.get(annotationClass);
                        Class<?> T = T_MAP.get(pluginValidator);
                        if (field.getType() != T) {
                            continue;
                        }

                        return true;
                    }
                    return false;
                })
                .toArray(Field[]::new);

        for (Field field : fields) {
            Annotation[] annotations = field.getAnnotations();
            for (Annotation annotation : annotations) {
                Class<? extends Annotation> annotationClass = annotation.getClass();
                if (!PLUGIN_VALIDATOR.containsKey(annotationClass)) {
                    continue;
                }

                PluginValidator pluginValidator = PLUGIN_VALIDATOR.get(annotationClass);
                pluginValidator.validate(field.get(obj), field.getName());
            }
        }
    }

    public int getPluginValidatorCount() {
        return PLUGIN_VALIDATOR.size();
    }

}

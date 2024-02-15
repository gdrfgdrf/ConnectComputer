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

package cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Annotation.TypeConvertorClass;
import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Base.BaseTypeConvertor;
import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.TypeConvertor;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractTypeConvertorManager {
    protected void initTypeConvertor() {
        initAnnotationTypeConvertor();
    }

    private void initAnnotationTypeConvertor() {
        TypeConvertorClass typeConvertorClass = getClass().getAnnotation(TypeConvertorClass.class);
        if (typeConvertorClass == null) {
            return;
        }

        Class<? extends BaseTypeConvertor>[] classes = typeConvertorClass.classes();
        for (Class<? extends BaseTypeConvertor> clazz : classes) {
            initTypeConvertor(clazz);
        }
    }

    private void initTypeConvertor(Class<? extends BaseTypeConvertor> clazz) {
        try {
            BaseTypeConvertor typeConvertor = clazz.getDeclaredConstructor().newInstance();
            Class<?> T = ClassUtils.getInterfaceT(typeConvertor, 0);

            TypeConvertor.INSTANCE.registerTypeConvertor(T, typeConvertor);

            log.info(
                    "Register type convertor {} for type {}",
                    clazz.getSimpleName(),
                    T.getSimpleName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

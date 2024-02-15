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

package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Annotation.ArgumentAssignorClass;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.ArgumentAssignorExecutor;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.ArgumentAssignor;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractArgumentAssignorManager {
    protected void initArgumentAssignor() {
        initAnnotationArgumentAssignor();
    }

    private void initAnnotationArgumentAssignor() {
        ArgumentAssignorClass argumentValidatorClass = getClass().getAnnotation(ArgumentAssignorClass.class);
        if (argumentValidatorClass == null) {
            return;
        }

        Class<? extends ArgumentAssignor>[] classes = argumentValidatorClass.classes();
        for (Class<? extends ArgumentAssignor> clazz : classes) {
            initArgumentAssignor(clazz);
        }
    }

    private void initArgumentAssignor(Class<? extends ArgumentAssignor> clazz) {
        try {
            ArgumentAssignor argumentAssignor = clazz.getDeclaredConstructor().newInstance();
            Class<?> T = ClassUtils.getInterfaceT(argumentAssignor, 0);

            ArgumentAssignorExecutor.INSTANCE.registerArgumentAssignor(T, argumentAssignor);

            log.info(
                    "Register argument assignor {} for type {}",
                    clazz.getSimpleName(),
                    T.getSimpleName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

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

package cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Annotation.ArgumentValidatorClass;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.ArgumentValidator;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.ArgumentValidatorExecutor;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractArgumentValidatorManager {
    protected void initArgumentValidator() {
        initAnnotationArgumentValidator();
    }

    private void initAnnotationArgumentValidator() {
        ArgumentValidatorClass argumentValidatorClass = getClass().getAnnotation(ArgumentValidatorClass.class);
        if (argumentValidatorClass == null) {
            return;
        }

        Class<? extends ArgumentValidator>[] classes = argumentValidatorClass.classes();
        for (Class<? extends ArgumentValidator> clazz : classes) {
            initArgumentValidator(clazz);
        }
    }

    private void initArgumentValidator(Class<? extends ArgumentValidator> clazz) {
        try {
            ArgumentValidator argumentValidator = clazz.getDeclaredConstructor().newInstance();
            Class<? extends Exception> T = (Class<? extends Exception>) ClassUtils.getInterfaceT(argumentValidator, 0);

            ArgumentValidatorExecutor.INSTANCE.registerArgumentParser(argumentValidator);

            log.info(
                    "Register argument validator {} for type {}",
                    clazz.getSimpleName(),
                    T.getSimpleName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}

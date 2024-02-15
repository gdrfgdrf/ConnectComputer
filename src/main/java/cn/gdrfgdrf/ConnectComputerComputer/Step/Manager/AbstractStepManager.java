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

package cn.gdrfgdrf.ConnectComputerComputer.Step.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Step.Annotation.StepClass;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;
import cn.gdrfgdrf.ConnectComputerComputer.Step.StepNavigator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractStepManager {
    protected void initStep() {
        initAnnotationStep();
    }

    private void initAnnotationStep() {
        StepClass stepClass = getClass().getAnnotation(StepClass.class);
        if (stepClass == null) {
            return;
        }

        Class<? extends Step>[] classes = stepClass.classes();
        String[] routes = stepClass.routes();
        for (int i = 0; i < classes.length; i++) {
            String route = routes[i];
            Class<? extends Step> clazz = classes[i];

            initStep(route, clazz);
        }
    }

    private void initStep(String route, Class<? extends Step> clazz) {
        try {
            Step step = clazz.getDeclaredConstructor().newInstance();
            StepNavigator.INSTANCE.addStep(route, step);

            log.info("Add step {}", clazz.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

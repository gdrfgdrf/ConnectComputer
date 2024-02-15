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

package cn.gdrfgdrf.ConnectComputerComputer.Step;

import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Callback.StepChainCallback;
import cn.gdrfgdrf.ConnectComputerComputer.Thread.ThreadPoolService;
import lombok.Getter;
import lombok.Setter;

import java.util.*;

/**
 * @author gdrfgdrf
 */
public enum StepNavigator {
    INSTANCE;

    private final Map<String, Step> STEP_MAP = new LinkedHashMap<>();
    private final List<String> ROUTE_LIST = new ArrayList<>();
    @Setter
    private DataStore dataStore;
    @Setter
    private StepChainCallback stepChainCallback;
    @Getter
    private Step currentStep;
    private String currentRoute;

    public void addStep(String route, Step step) {
        STEP_MAP.put(route, step);
        ROUTE_LIST.add(route);
    }

    public void startStep(String route) {
        if (dataStore == null) {
            return;
        }
        Step step = STEP_MAP.get(route);
        if (step != null) {
            currentRoute = route;
            try {
                step.start(dataStore);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            currentStep = step;
        }
    }

    public void stepFailed() {
        if (stepChainCallback != null) {
            stepChainCallback.onFailed();
        }
    }

    public void nextStep() {
        if (dataStore == null) {
            return;
        }
        int currentRouteIndex = ROUTE_LIST.indexOf(currentRoute);
        if (currentRouteIndex >= ROUTE_LIST.size() - 1) {
            currentStep = null;
            currentRoute = null;
            if (stepChainCallback != null) {
                stepChainCallback.onAllFinished();
            }
            return;
        }

        currentRouteIndex++;
        String route = ROUTE_LIST.get(currentRouteIndex);
        currentRoute = route;
        Step step = STEP_MAP.get(route);

        ThreadPoolService.newTask(() -> {
            currentStep = step;
            try {
                step.start(dataStore);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void startCurrentStepAgain() {
        if (dataStore == null) {
            return;
        }
        try {
            currentStep.start(dataStore);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}


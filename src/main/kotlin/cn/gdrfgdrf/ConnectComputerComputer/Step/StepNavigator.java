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


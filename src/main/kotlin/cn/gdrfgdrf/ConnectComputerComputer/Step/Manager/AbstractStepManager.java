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

            log.info("Register step {}", clazz.getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

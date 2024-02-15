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

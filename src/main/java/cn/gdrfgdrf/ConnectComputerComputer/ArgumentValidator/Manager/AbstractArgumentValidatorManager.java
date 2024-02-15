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

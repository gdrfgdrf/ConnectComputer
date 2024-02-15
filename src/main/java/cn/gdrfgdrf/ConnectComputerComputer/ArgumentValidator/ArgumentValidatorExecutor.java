package cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Annotation.ArgumentValidated;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Exception.ArgumentValidatorExecuteException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AppUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@Slf4j
public enum ArgumentValidatorExecutor {
    INSTANCE;

    private final Map<Class<? extends ArgumentValidator>, ArgumentValidator<?>> ARGUMENT_VALIDATOR = new HashMap<>();

    public void registerArgumentParser(ArgumentValidator<?> argumentValidator) {
        ARGUMENT_VALIDATOR.put(argumentValidator.getClass(), argumentValidator);
    }

    public void validate(Object obj) throws ArgumentValidatorExecuteException {
        Field[] fields = obj.getClass().getDeclaredFields();
        List<Field> argumentField = new LinkedList<>();

        for (Field field : fields) {
            if (!field.isAnnotationPresent(ArgumentValidated.class)) {
                continue;
            }
            argumentField.add(field);
        }

        boolean error = false;
        for (Field field : argumentField) {
            ArgumentValidated argumentValidated = field.getAnnotation(ArgumentValidated.class);
            assert argumentValidated != null;

            Class<? extends ArgumentValidator> clazz = argumentValidated.validator();
            ArgumentValidator validator = ARGUMENT_VALIDATOR.get(clazz);
            if (validator == null) {
                throw new ArgumentValidatorExecuteException("Not found the ArgumentValidator by " + clazz.getSimpleName());
            }

            Object argument = null;
            try {
                boolean changeAccessible = false;
                if (!field.canAccess(obj)) {
                    field.setAccessible(true);
                    changeAccessible = true;
                }

                argument = field.get(obj);

                if (argument == null) {
                    log.info(
                            AppLocale.ARGUMENT_SKIP_THE_VALIDATOR,
                            validator.getShownName()
                    );

                    if (changeAccessible) {
                        field.setAccessible(false);
                        continue;
                    }
                }

                boolean result = validator.validate(argument);
                if (!result) {
                    log.error(
                            AppLocale.ARGUMENT_CANNOT_PASS_THE_VALIDATOR,
                            validator.getShownName()
                    );
                    if (!StringUtils.isBlank(validator.getErrorMessage())) {
                        log.error(
                                AppLocale.ARGUMENT_ERROR_MESSAGE,
                                validator.getShownName(),
                                validator.getErrorMessage()
                        );
                    }

                    error = true;
                } else {
                    log.info(
                            AppLocale.ARGUMENT_PASS_THE_VALIDATOR,
                            validator.getShownName()
                    );
                }

                if (changeAccessible) {
                    field.setAccessible(false);
                }
            } catch (Exception ignored) {
                log.error(
                        AppLocale.ARGUMENT_VALIDATOR_THROW_EXCEPTION,
                        validator.getShownName()
                );
                error = true;
            }
        }

        if (error) {
            log.error(AppLocale.ARGUMENT_TYPE_ERROR);
            AppUtils.exitProgram();
        }
    }
}

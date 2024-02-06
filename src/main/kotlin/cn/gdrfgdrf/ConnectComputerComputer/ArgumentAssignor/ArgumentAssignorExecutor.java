package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Annotation.Argument;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Exception.ArgumentAssignorExecuteException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AppUtils;
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
public enum ArgumentAssignorExecutor {
    INSTANCE;

    private final Map<Class<?>, ArgumentAssignor> TYPE_AND_INSTANCE = new HashMap<>();

    public void registerArgumentAssignor(Class<?> type, ArgumentAssignor argumentAssignor) {
        TYPE_AND_INSTANCE.put(type, argumentAssignor);
    }

    public void assign(Object obj, Map<String, String> map) throws ArgumentAssignorExecuteException {
        List<String> fieldNameList = new LinkedList<>();
        Map<String, String> argumentMap = new HashMap<>();

        Field[] fields = obj.getClass().getDeclaredFields();
        Map<String, Field> fieldMap = new HashMap<>();

        for (Field field : fields) {
            fieldNameList.add(field.getName());
        }

        for (Map.Entry<String, String> entry : map.entrySet()) {
            String name = entry.getKey();
            if (fieldNameList.contains(name)) {
                argumentMap.put(name, entry.getValue());
            }
        }

        for (Field field : fields) {
            if (!field.isAnnotationPresent(Argument.class)) {
                continue;
            }
            if (!argumentMap.containsKey(field.getName())) {
                continue;
            }

            fieldMap.put(field.getName(), field);
        }

        boolean error = false;
        for (Map.Entry<String, Field> entry : fieldMap.entrySet()) {
            String fieldName = entry.getKey();
            Field field = entry.getValue();
            String argument = argumentMap.get(fieldName);

            if (!field.isAnnotationPresent(Argument.class)) {
                continue;
            }

            boolean assignError = assignArgument(obj, field, argument);
            if (assignError) {
                error = true;
            } else {
                log.info(
                        AppLocale.ARGUMENT_PASS_THE_ASSIGNOR,
                        fieldName
                );
            }
        }

        if (error) {
            log.error(AppLocale.ARGUMENT_TYPE_ERROR);
            AppUtils.exitProgram();
        }
    }

    private boolean assignArgument(Object obj, Field field, String argument) throws ArgumentAssignorExecuteException {
        Class<?> type = field.getType();
        ArgumentAssignor argumentAssignor = TYPE_AND_INSTANCE.get(type);
        if (argumentAssignor == null) {
            throw new ArgumentAssignorExecuteException("Not found the ArgumentAssignor by type " + type.getSimpleName());
        }

        boolean error = false;
        try {
            boolean changeAccessible = false;
            if (!field.canAccess(obj)) {
                field.setAccessible(true);
                changeAccessible = true;
            }

            boolean result = argumentAssignor.assign(obj, field, argument);
            if (!result) {
                log.error(
                        AppLocale.ARGUMENT_CANNOT_SKIP_THE_ASSIGNOR,
                        argumentAssignor.getShownName()
                );
                error = true;
            }

            if (changeAccessible) {
                field.setAccessible(false);
            }
        } catch (Exception ignored) {
            log.error(
                    AppLocale.ARGUMENT_ASSIGNOR_THROW_EXCEPTION,
                    argumentAssignor.getShownName()
            );
            error = true;
        }

        return error;
    }
}

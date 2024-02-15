package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Assignor;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.ArgumentAssignor;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;

import java.lang.reflect.Field;

/**
 * @author gdrfgdrf
 */
public class BooleanArgumentAssignor implements ArgumentAssignor<Boolean> {
    @Override
    public boolean assign(Object obj, Field field, Object argument) throws Exception {
        String str = argument.toString();

        if ("true".equals(str)) {
            field.set(obj, true);
            return true;
        }
        if ("false".equals(str)) {
            field.set(obj, false);
            return true;
        }
        return false;
    }

    @Override
    public String getShownName() {
        return AppLocale.ARGUMENT_BOOLEAN_ASSIGNOR;
    }
}

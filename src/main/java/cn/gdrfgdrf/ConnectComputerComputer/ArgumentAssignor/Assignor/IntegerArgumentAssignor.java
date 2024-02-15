package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Assignor;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.ArgumentAssignor;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;

import java.lang.reflect.Field;

/**
 * @author gdrfgdrf
 */
public class IntegerArgumentAssignor implements ArgumentAssignor<Integer> {
    @Override
    public boolean assign(Object obj, Field field, Object argument) throws IllegalAccessException {
        String str = argument.toString();
        if (!StringUtils.checkStringForInteger(str)) {
            return false;
        }

        Integer result = Integer.parseInt(str);
        field.set(obj, result);

        return true;
    }

    @Override
    public String getShownName() {
        return AppLocale.ARGUMENT_INTEGER_ASSIGNOR;
    }
}

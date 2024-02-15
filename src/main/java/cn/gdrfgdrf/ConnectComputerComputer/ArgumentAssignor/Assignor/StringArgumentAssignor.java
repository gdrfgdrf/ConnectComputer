package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Assignor;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.ArgumentAssignor;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;

import java.lang.reflect.Field;

/**
 * @author gdrfgdrf
 */
public class StringArgumentAssignor implements ArgumentAssignor<String> {
    @Override
    public boolean assign(Object obj, Field field, Object argument) throws Exception {
        field.set(obj, argument.toString());
        return true;
    }

    @Override
    public String getShownName() {
        return AppLocale.ARGUMENT_STRING_ASSIGNOR;
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Base.Validator;

/**
 * @author gdrfgdrf
 */
public class IntegerPositiveValidator implements Validator<Integer> {
    @Override
    public boolean validate(Integer argument) {
        return argument >= 0;
    }

    @Override
    public String getInvalidMessage() {
        return AppLocale.INTEGER_POSITIVE_ERROR;
    }
}

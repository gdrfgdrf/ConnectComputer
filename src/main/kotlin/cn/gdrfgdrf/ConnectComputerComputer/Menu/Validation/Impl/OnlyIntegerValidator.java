package cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Base.Validator;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;

/**
 * @author gdrfgdrf
 */
public class OnlyIntegerValidator implements Validator<String> {
    @Override
    public boolean validate(String argument) {
        return StringUtils.checkStringForInteger(argument);
    }

    @Override
    public String getInvalidMessage() {
        return AppLocale.MENU_USER_INPUT_VALIDATOR_ONLY_INTEGER_INVALID;
    }
}

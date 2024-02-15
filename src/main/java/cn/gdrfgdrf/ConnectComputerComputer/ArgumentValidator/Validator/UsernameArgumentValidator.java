package cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Validator;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.ArgumentValidator;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;

/**
 * @author gdrfgdrf
 */
public class UsernameArgumentValidator implements ArgumentValidator<String> {
    @Override
    public boolean validate(String argument) {
        return StringUtils.verifyByPattern(argument, Constants.USERNAME_REGEX);
    }

    @Override
    public String getShownName() {
        return AppLocale.ARGUMENT_USERNAME_VALIDATOR;
    }

    @Override
    public String getErrorMessage() {
        return AppLocale.ARGUMENT_USERNAME_VALIDATOR_ERROR;
    }
}

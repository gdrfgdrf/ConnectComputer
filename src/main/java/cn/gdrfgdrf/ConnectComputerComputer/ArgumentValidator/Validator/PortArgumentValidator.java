package cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Validator;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.ArgumentValidator;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.NetworkUtils;

/**
 * @author gdrfgdrf
 */
public class PortArgumentValidator implements ArgumentValidator<Integer> {
    @Override
    public boolean validate(Integer argument) {
        return NetworkUtils.isValidPort(argument);
    }

    @Override
    public String getShownName() {
        return AppLocale.ARGUMENT_PORT_VALIDATOR;
    }

    @Override
    public String getErrorMessage() {
        return AppLocale.ARGUMENT_PORT_VALIDATOR_ERROR;
    }
}

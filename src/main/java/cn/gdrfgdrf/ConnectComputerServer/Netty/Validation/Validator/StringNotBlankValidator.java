package cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Validator;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Base.Validator;
import cn.gdrfgdrf.ConnectComputerServer.Utils.StringUtils;

/**
 * @author gdrfgdrf
 */
public class StringNotBlankValidator implements Validator<String> {
    @Override
    public boolean validate(String argument, Object args) {
        return !StringUtils.isBlank(argument);
    }
}

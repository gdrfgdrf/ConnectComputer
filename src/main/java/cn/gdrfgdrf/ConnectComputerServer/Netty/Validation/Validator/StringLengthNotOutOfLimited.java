package cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Validator;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Base.Validator;

/**
 * @author gdrfgdrf
 */
public class StringLengthNotOutOfLimited implements Validator<String> {
    @Override
    public boolean validate(String argument, Object args) {
        int limit = (int) args;
        return argument.length() <= limit;
    }
}

package cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Validator;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Base.Validator;

/**
 * @author gdrfgdrf
 */
public class NotNullValidator implements Validator<Object> {
    @Override
    public boolean validate(Object argument, Object args) {
        return argument != null;
    }
}

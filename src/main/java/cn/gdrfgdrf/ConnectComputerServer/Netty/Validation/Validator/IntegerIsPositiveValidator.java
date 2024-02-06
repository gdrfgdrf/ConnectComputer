package cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Validator;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Base.Validator;

/**
 * @author gdrfgdrf
 */
public class IntegerIsPositiveValidator implements Validator<Integer> {
    @Override
    public boolean validate(Integer argument, Object args) {
        return argument >= 0;
    }
}

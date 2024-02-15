package cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Base.Validator;

public class NotNullValidator implements Validator<Object> {
    @Override
    public boolean validate(Object argument) {
        return argument != null;
    }

    @Override
    public String getInvalidMessage() {
        return AppLocale.NOT_NULL;
    }
}

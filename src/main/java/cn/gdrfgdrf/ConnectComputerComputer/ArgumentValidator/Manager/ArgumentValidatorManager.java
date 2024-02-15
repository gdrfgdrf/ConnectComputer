package cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Annotation.ArgumentValidatorClass;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Validator.PortArgumentValidator;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Validator.UsernameArgumentValidator;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;

/**
 * @author gdrfgdrf
 */
@ArgumentValidatorClass(classes = {
        PortArgumentValidator.class,
        UsernameArgumentValidator.class
})
public class ArgumentValidatorManager extends AbstractArgumentValidatorManager implements Bean {
    @Override
    public void init() throws Exception {
        super.initArgumentValidator();
    }
}

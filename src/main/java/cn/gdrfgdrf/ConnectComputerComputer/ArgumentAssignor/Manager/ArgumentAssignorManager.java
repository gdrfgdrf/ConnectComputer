package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Annotation.ArgumentAssignorClass;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Assignor.BooleanArgumentAssignor;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Assignor.IntegerArgumentAssignor;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Assignor.StringArgumentAssignor;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;

/**
 * @author gdrfgdrf
 */
@ArgumentAssignorClass(classes = {
        IntegerArgumentAssignor.class,
        StringArgumentAssignor.class,
        BooleanArgumentAssignor.class
})
public class ArgumentAssignorManager extends AbstractArgumentAssignorManager implements Bean {
    @Override
    public void init() throws Exception {
        super.initArgumentAssignor();
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Annotation.TypeConvertorClass;
import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Impl.IntegerTypeConvertor;

/**
 * @author gdrfgdrf
 */
@TypeConvertorClass(classes = {
        IntegerTypeConvertor.class
})
public class TypeConvertorManager extends AbstractTypeConvertorManager implements Bean {
    @Override
    public void init() throws Exception {
        super.initTypeConvertor();
    }
}

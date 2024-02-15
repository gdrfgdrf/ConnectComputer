package cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Base.BaseTypeConvertor;

/**
 * @author gdrfgdrf
 */
public class IntegerTypeConvertor implements BaseTypeConvertor<Integer> {
    @Override
    public Integer convert(Object obj) {
        return Integer.parseInt(obj.toString());
    }
}

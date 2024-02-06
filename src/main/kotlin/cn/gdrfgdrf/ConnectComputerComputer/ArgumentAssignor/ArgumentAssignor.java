package cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor;

import java.lang.reflect.Field;

/**
 * @author gdrfgdrf
 */
public interface ArgumentAssignor<T> {
    boolean assign(Object obj, Field field, Object argument) throws Exception;
    String getShownName();
}

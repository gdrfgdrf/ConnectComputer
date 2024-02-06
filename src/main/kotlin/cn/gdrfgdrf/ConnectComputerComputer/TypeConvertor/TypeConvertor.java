package cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor;

import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Base.BaseTypeConvertor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gdrfgdrf
 */
public enum TypeConvertor {
    INSTANCE;

    private final Map<Class<?>, BaseTypeConvertor<?>> CONVERTOR_MAP = new ConcurrentHashMap<>();

    public void registerTypeConvertor(Class<?> T, BaseTypeConvertor<?> typeConvertor) {
        CONVERTOR_MAP.put(T, typeConvertor);
    }

    public Object convert(Object obj, Class<?> target) {
        BaseTypeConvertor convertor = CONVERTOR_MAP.get(target);
        if (convertor == null) {
            throw new NullPointerException("Not found type convertor by target " + target);
        }

        return convertor.convert(obj);
    }
}

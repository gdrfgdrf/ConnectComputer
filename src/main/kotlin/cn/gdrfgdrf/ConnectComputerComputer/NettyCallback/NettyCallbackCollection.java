package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback;

import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gdrfgdrf
 */
public enum NettyCallbackCollection {
    INSTANCE;

    private final Map<Class<?>, NettyCallback> CALLBACK_MAP = new ConcurrentHashMap<>();

    public void registerNettyCallback(Class<?> target, NettyCallback nettyCallback) {
        CALLBACK_MAP.put(target, nettyCallback);
    }

    public <T> T getNettyCallback(Class<?> clazz) {
        return (T) CALLBACK_MAP.get(clazz);
    }
}

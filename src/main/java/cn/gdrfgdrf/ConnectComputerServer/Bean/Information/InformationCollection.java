package cn.gdrfgdrf.ConnectComputerServer.Bean.Information;

import lombok.Getter;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author gdrfgdrf
 */
public class InformationCollection {
    @Getter
    private static final Map<String, Class<? extends Information>> MAP = new ConcurrentHashMap<>();
}

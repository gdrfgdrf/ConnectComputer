package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Utils;

import cn.gdrfgdrf.ConnectComputerComputer.Utils.ClassUtils;
import com.google.protobuf.GeneratedMessageV3;

import java.io.File;
import java.util.*;
import java.util.function.Predicate;

/**
 * @author gdrfgdrf
 */
public class ClassScanner {
    private static final String PACKAGE_NAME = "cn.gdrfgdrf.Protobuf";
    private static final Set<Class<? extends GeneratedMessageV3>> DEFAULT_CLASSES_SET = new HashSet<>();

    public static Set<Class<? extends GeneratedMessageV3>> lookupClasses() {
        if (DEFAULT_CLASSES_SET.isEmpty()) {
            Set<Class<?>> temp = new HashSet<>();
            ClassUtils.searchJar(PACKAGE_NAME, clazz -> {
                try {
                    if (clazz.asSubclass(GeneratedMessageV3.class) != null) {
                        return true;
                    }
                } catch (Exception ignored) {
                }

                return false;
            }, temp);
            temp.forEach(clazz -> {
                DEFAULT_CLASSES_SET.add((Class<? extends GeneratedMessageV3>) clazz);
            });
        }
        return DEFAULT_CLASSES_SET;
    }
}

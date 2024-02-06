package cn.gdrfgdrf.ConnectComputerServer.Netty.Utils;

import com.google.protobuf.GeneratedMessageV3;
import org.reflections.Reflections;

import java.util.HashSet;
import java.util.Set;

/**
 * @author gdrfgdrf
 */
public class ClassScanner {
    private static final Reflections DEFAULT_REFLECTIONS = new Reflections("cn.gdrfgdrf.Protobuf");
    private static final Set<Class<? extends GeneratedMessageV3>> DEFAULT_CLASSES_SET = new HashSet<>();

    public static Set<Class<? extends GeneratedMessageV3>> lookupClasses(Class<GeneratedMessageV3> subType) {
        if (DEFAULT_CLASSES_SET.isEmpty()) {
            DEFAULT_CLASSES_SET.addAll(DEFAULT_REFLECTIONS.getSubTypesOf(subType));
        }
        return DEFAULT_CLASSES_SET;
    }

    public static <T> Set<Class<? extends T>> lookupClasses(Class<T> subType, String... basePackages) {
        Reflections reflections = new Reflections(basePackages);
        return reflections.getSubTypesOf(subType);
    }

    public static <T> Set<Class<? extends T>> lookupClasses(Class<T> subType, Class<?>... basePackageClasses) {
        String[] basePackages = new String[basePackageClasses.length];
        for(int i = 0; i < basePackageClasses.length; i ++) {
            basePackages[i] = basePackageClasses[i].getPackage().getName();
        }
        return lookupClasses(subType, basePackages);
    }
}

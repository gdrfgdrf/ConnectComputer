package cn.gdrfgdrf.ConnectComputerComputer.Utils;

import kotlin.coroutines.Continuation;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;

/**
 * @author gdrfgdrf
 */
public class ClassUtils {
    private ClassUtils() {}

    public static Class<?> getInterfaceT(Object o, int index) {
        Type[] types = o.getClass().getGenericInterfaces();
        ParameterizedType parameterizedType = (ParameterizedType) types[index];
        Type type = parameterizedType.getActualTypeArguments()[index];

        return checkType(type, index);
    }

    private static Class<?> checkType(Type type, int index) {
        if (type instanceof Class<?>) {
            return (Class<?>) type;
        } else if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType) type;
            Type t = pt.getActualTypeArguments()[index];
            return checkType(t, index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType"
                    + ", but <" + type + "> is of type " + className);
        }
    }

    static boolean aBoolean = false;

    public static Method getMethod(Class<?> clazz, String methodName, Class<?>... args) {
        if (!aBoolean) {
            aBoolean = true;

            Method[] methods = clazz.getMethods();
            for (Method method : methods) {
                System.out.println(method);
            }
        }
        try {
            return clazz.getMethod(methodName, args);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Method getKotlinCoroutineMethod(Class<?> clazz, String methodName, Class<?>... args) {
        Class<?>[] finalArgs = Arrays.copyOf(args, args.length + 1);
        finalArgs[args.length] = Continuation.class;
        return getMethod(clazz, methodName, finalArgs);
    }
}

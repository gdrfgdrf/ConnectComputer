package cn.gdrfgdrf.ConnectComputerComputer.Utils;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Set;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

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
            ParameterizedType parameterizedType = (ParameterizedType) type;
            Type t = parameterizedType.getActualTypeArguments()[index];
            return checkType(t, index);
        } else {
            String className = type == null ? "null" : type.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType"
                    + ", but <" + type + "> is of type " + className);
        }
    }

    public static void search(
            File searchRoot,
            String packageName,
            Predicate<Class<?>> predicate,
            Set<Class<?>> result
    ) {
        searchInternal(searchRoot, packageName, predicate, result, true);
    }

    private static void searchInternal(
            File searchRoot,
            String packageName,
            Predicate<Class<?>> predicate,
            Set<Class<?>> result,
            boolean flag
    ) {
        if (searchRoot.isDirectory()) {
            File[] files = searchRoot.listFiles();
            if (files == null) {
                return;
            }
            if (!flag) {
                packageName = packageName + "." + searchRoot.getName();
            }

            String finalPackageName = packageName;
            Arrays.stream(files).forEach(file -> {
                searchInternal(file, finalPackageName, predicate, result, false);
            });

            return;
        }
        if (searchRoot.getName().endsWith(".class")) {
            try {
                Class<?> clazz = Class.forName(packageName + "." +
                        searchRoot.getName().substring(
                                0,
                                searchRoot.getName().lastIndexOf(".")
                        ));
                if (predicate == null || predicate.test(clazz)) {
                    result.add(clazz);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void searchJar(
            String packageName,
            Predicate<Class<?>> predicate,
            Set<Class<?>> result
    ) {
        try {
            Enumeration<URL> urlEnumeration = Thread.currentThread().getContextClassLoader()
                    .getResources(packageName.replace(".", "/"));

            while (urlEnumeration.hasMoreElements()) {
                URL url = urlEnumeration.nextElement();
                String protocol = url.getProtocol();

                if (!"jar".equalsIgnoreCase(protocol)) {
                    if ("file".equalsIgnoreCase(protocol)) {
                        String classpath = url.getPath().replace(
                                packageName.replace(".", "/"),
                                ""
                        );
                        String packagePath = packageName.replace(".", "/");
                        File searchRoot = new File(classpath + packagePath);

                        search(searchRoot, packageName, predicate, result);
                    }
                    continue;
                }
                JarURLConnection connection = (JarURLConnection) url.openConnection();
                if (connection == null) {
                    continue;
                }
                JarFile jarFile = connection.getJarFile();
                if (jarFile == null) {
                    continue;
                }

                Enumeration<JarEntry> entryEnumeration = jarFile.entries();
                while (entryEnumeration.hasMoreElements()) {
                    JarEntry entry = entryEnumeration.nextElement();
                    String entryName = entry.getName();
                    if (!entryName.contains(".class") ||
                            !entryName.replaceAll("/", ".").startsWith(packageName)) {
                        continue;
                    }
                    String className = entryName.substring(0, entryName.lastIndexOf("."))
                            .replace("/", ".");
                    Class<?> clazz = Class.forName(className);

                    if (predicate == null || predicate.test(clazz)) {
                        result.add(clazz);
                    }
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

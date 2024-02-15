/*
 * Copyright (C) 2024 Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

package cn.gdrfgdrf.ConnectComputerComputer.Interceptor;

import cn.gdrfgdrf.ConnectComputerComputer.Annotation.Keep;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataBean;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Annotation.BeanMethodInterceptor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@BeanMethodInterceptor(
        methodName = "createInstance",
        args = {
                Class.class
        }
)
public class DataStoreCreateOnGetIfNotExistMethodInterceptor implements MethodInterceptor {
    private final Map<Method, Field> methods = new HashMap<>();

    private DataStoreCreateOnGetIfNotExistMethodInterceptor() {}

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
        if (!(obj instanceof DataStore dataStore)) {
            return proxy.invokeSuper(obj, args);
        }
        if (!methods.containsKey(method)) {
            return proxy.invokeSuper(obj, args);
        }
        Field field = methods.get(method);
        dataStore.createDataBean(field);

        return proxy.invokeSuper(obj, args);
    }

    @Keep
    public static <T> T createInstance(Class<? extends DataStore> clazz) throws NoSuchMethodException {
        DataStoreCreateOnGetIfNotExistMethodInterceptor interceptor = new DataStoreCreateOnGetIfNotExistMethodInterceptor();
        interceptor.methods.putAll(filterMethod(clazz));

        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(interceptor);

        return (T) enhancer.create();
    }

    private static Map<Method, Field> filterMethod(Class<? extends DataStore> clazz) throws NoSuchMethodException {
        Map<Method, Field> result = new HashMap<>();
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            try {
                field.getType().asSubclass(DataBean.class);
            } catch (Exception ignored) {
                continue;
            }
            if (!DataStore.checkCreateOnGetIfNotExist(field)) {
                continue;
            }

            String methodName = getGetterMethodName(field);
            Method method = clazz.getDeclaredMethod(methodName);
            result.put(method, field);
        }
        return result;
    }

    private static String getGetterMethodName(Field field) {
        String fieldName = StringUtils.capitalize(field.getName());
        return "get" + fieldName;
    }
}

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

import cn.gdrfgdrf.ConnectComputerComputer.Menu.Menu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Annotation.Validated;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Base.Validator;
import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.TypeConvertor;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class MenuMethodInterceptor implements MethodInterceptor {
    public static final MenuMethodInterceptor INSTANCE = new MenuMethodInterceptor();
    private static final Map<Class<? extends Validator>, Validator> INSTANCE_MAP = new HashMap<>();

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        if (!method.isAnnotationPresent(Validated.class)) {
            return invoke(o, method, objects, methodProxy);
        }
        Validated[] validatedArray = method.getAnnotationsByType(Validated.class);

        for (int validatedIndex = 0; validatedIndex < validatedArray.length; validatedIndex++) {
            Validated validated = validatedArray[validatedIndex];
            Class<? extends Validator>[] validatorClazz = validated.validator();
            Validator[] validators = new Validator[validatorClazz.length];

            for (int classIndex = 0; classIndex < validatorClazz.length; classIndex++) {
                Class<? extends Validator> clazz = validatorClazz[classIndex];

                if (!INSTANCE_MAP.containsKey(clazz)) {
                    validators[classIndex] = clazz.getDeclaredConstructor().newInstance();
                    INSTANCE_MAP.put(clazz, validators[classIndex]);
                } else {
                    validators[classIndex] = INSTANCE_MAP.get(clazz);
                }
            }

            for (Validator validator : validators) {
                Object obj = objects[validatedIndex];
                Class<?> T = ClassUtils.getInterfaceT(validator, 0);

                if (T != Object.class && obj.getClass() != T) {
                    obj = TypeConvertor.INSTANCE.convert(obj, T);
                }

                if (!validator.validate(obj)) {
                    log.error(validator.getInvalidMessage());
                    return null;
                }
            }
        }

        return invoke(o, method, objects, methodProxy);
    }

    private Object invoke(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        Menu menu = (Menu) o;
        Object result = null;
        if (!"userInput".equals(method.getName())) {
            result = methodProxy.invokeSuper(o, objects);
        }

        if ("popup".equals(method.getName()) ||
                "popupAgain".equals(method.getName())) {
            menu.setPopupFinished(true);
            synchronized (menu.getLock()) {
                menu.getLock().notifyAll();
            }
        }
        if ("dismiss".equals(method.getName())) {
            menu.setPopupFinished(false);
        }
        if ("userInput".equals(method.getName())) {
            if (menu.isPopupFinished()) {
                result = methodProxy.invokeSuper(o, objects);
            } else {
                synchronized (menu.getLock()) {
                    menu.getLock().wait();
                    result = methodProxy.invokeSuper(o, objects);
                }
            }
        }

        return result;
    }

    public <T> T createInstance(Class<? extends Menu> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }
}

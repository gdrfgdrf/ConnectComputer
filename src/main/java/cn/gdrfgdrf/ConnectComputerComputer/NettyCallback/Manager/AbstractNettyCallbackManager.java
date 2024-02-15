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

package cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Interceptor.AsyncMethodInterceptor;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackClass;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Annotation.CallbackInfo;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Base.NettyCallback;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.NettyCallbackCollection;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractNettyCallbackManager {
    protected void initNettyCallback() {
        initAnnotationNettyCallback();
    }

    private void initAnnotationNettyCallback() {
        CallbackClass callbackClass = getClass().getAnnotation(CallbackClass.class);
        if (callbackClass == null) {
            return;
        }

        Class<? extends NettyCallback>[] classes = callbackClass.classes();
        for (Class<? extends NettyCallback> clazz : classes) {
            initNettyCallback(clazz);
        }
    }

    private void initNettyCallback(Class<? extends NettyCallback> clazz) {
        try {
            if (!clazz.isAnnotationPresent(CallbackInfo.class)) {
                return;
            }
            CallbackInfo callbackInfo = clazz.getAnnotation(CallbackInfo.class);
            assert callbackInfo != null;

            Class<?> target = callbackInfo.targetClass();
            Class<?> implemented = callbackInfo.implementedClass();

            NettyCallback instance = AsyncMethodInterceptor.INSTANCE.createInstance(implemented);
            NettyCallbackCollection.INSTANCE.registerNettyCallback(target, instance);

            log.info(
                    "Register the implementation class {} for the target class {}",
                    implemented.getSimpleName(),
                    target.getSimpleName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

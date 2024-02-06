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

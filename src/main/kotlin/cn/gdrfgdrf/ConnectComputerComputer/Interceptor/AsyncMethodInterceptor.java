package cn.gdrfgdrf.ConnectComputerComputer.Interceptor;

import cn.gdrfgdrf.ConnectComputerComputer.Thread.ThreadPoolService;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author gdrfgdrf
 */
public class AsyncMethodInterceptor implements MethodInterceptor {
    public static final AsyncMethodInterceptor INSTANCE = new AsyncMethodInterceptor();

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        ThreadPoolService.newTask(() -> {
            try {
                methodProxy.invokeSuper(o, objects);
            } catch (Throwable e) {
                throw new RuntimeException(e);
            }
        });

        return null;
    }

    public <T> T createInstance(Class<?> clazz) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return (T) enhancer.create();
    }
}

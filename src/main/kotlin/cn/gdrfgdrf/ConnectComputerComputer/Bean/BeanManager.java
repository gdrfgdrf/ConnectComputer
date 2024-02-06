package cn.gdrfgdrf.ConnectComputerComputer.Bean;

import cn.gdrfgdrf.ConnectComputerComputer.ArgumentAssignor.Manager.ArgumentAssignorManager;
import cn.gdrfgdrf.ConnectComputerComputer.ArgumentValidator.Manager.ArgumentValidatorManager;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.InformationCollection;
import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.PacketHandler.Manager.PacketHandlerManager;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Manager.ExceptionManager;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Config;
import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.GlobalUncaughtExceptionHandler;
import cn.gdrfgdrf.ConnectComputerComputer.Language.Loader.LocaleLoader;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Manager.MenuManager;
import cn.gdrfgdrf.ConnectComputerComputer.NettyCallback.Manager.NettyCallbackManager;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Annotation.BeanInfo;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Annotation.BeanMethodInterceptor;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Annotation.BeanOrder;
import cn.gdrfgdrf.ConnectComputerComputer.Bean.Annotation.BeanUseMethodInterceptor;
import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Manager.TypeConvertorManager;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.Method;
import java.util.*;

/**
 * @author gdrfgdrf
 */
@Slf4j
@BeanOrder(
        order = {
                GlobalUncaughtExceptionHandler.class,
                ExceptionManager.class,

                DataStore.class,
                Config.class,
                LocaleLoader.class,

                InformationCollection.class,

                TypeConvertorManager.class,
                ArgumentAssignorManager.class,
                ArgumentValidatorManager.class,
                MenuManager.class,

                PacketHandlerManager.class,
                NettyCallbackManager.class
        }
)
public class BeanManager {
    private static BeanManager INSTANCE;

    private final Map<String, Bean> BEAN_MAP = new HashMap<>();

    public static BeanManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BeanManager();
        }
        return INSTANCE;
    }

    private BeanManager() {}

    public <T> T getBean(String name) {
        return (T) BEAN_MAP.get(name);
    }

    public void init() {
        if (!BeanManager.class.isAnnotationPresent(BeanOrder.class)) {
            return;
        }

        BeanOrder beanOrder = BeanManager.class.getAnnotation(BeanOrder.class);
        assert beanOrder != null;

        log.info("========================= Bean Manager =========================");

        Class<? extends Bean>[] classes = beanOrder.order();
        for (Class<? extends Bean> taskClazz : classes) {
            String name = taskClazz.getSimpleName();

            if (taskClazz.isAnnotationPresent(BeanInfo.class)) {
                BeanInfo beanInfo = taskClazz.getAnnotation(BeanInfo.class);
                assert beanInfo != null;

                name = beanInfo.name();
            }

            log.info("{} ...", name);

            try {
                Bean instance;

                if (taskClazz.isAnnotationPresent(BeanUseMethodInterceptor.class)) {
                    BeanUseMethodInterceptor beanUseMethodInterceptor = taskClazz.getAnnotation(BeanUseMethodInterceptor.class);
                    assert beanUseMethodInterceptor != null;

                    Class<?> interceptorClass = beanUseMethodInterceptor.interceptorClass();
                    BeanMethodInterceptor beanMethodInterceptor = interceptorClass.getAnnotation(BeanMethodInterceptor.class);
                    String methodName = beanMethodInterceptor.methodName();
                    Class<?>[] args = beanMethodInterceptor.args();

                    Method method = interceptorClass.getMethod(methodName, args);
                    instance = (Bean) method.invoke(null, taskClazz);
                } else {
                    instance = taskClazz.getConstructor().newInstance();
                }

                instance.run();

                BEAN_MAP.put(name, instance);

                log.info("{} √", name);
            } catch (Exception e) {
                e.printStackTrace();
                log.error("{} error: {}", name, e.getMessage());
            }

            System.out.println();
        }

        log.info("========================= Bean Manager =========================");
    }
}

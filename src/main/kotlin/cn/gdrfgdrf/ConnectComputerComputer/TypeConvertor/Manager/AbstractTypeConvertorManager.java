package cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Annotation.TypeConvertorClass;
import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.Base.BaseTypeConvertor;
import cn.gdrfgdrf.ConnectComputerComputer.TypeConvertor.TypeConvertor;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.ClassUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractTypeConvertorManager {
    protected void initTypeConvertor() {
        initAnnotationTypeConvertor();
    }

    private void initAnnotationTypeConvertor() {
        TypeConvertorClass typeConvertorClass = getClass().getAnnotation(TypeConvertorClass.class);
        if (typeConvertorClass == null) {
            return;
        }

        Class<? extends BaseTypeConvertor>[] classes = typeConvertorClass.classes();
        for (Class<? extends BaseTypeConvertor> clazz : classes) {
            initTypeConvertor(clazz);
        }
    }

    private void initTypeConvertor(Class<? extends BaseTypeConvertor> clazz) {
        try {
            BaseTypeConvertor typeConvertor = clazz.getDeclaredConstructor().newInstance();
            Class<?> T = ClassUtils.getInterfaceT(typeConvertor, 0);

            TypeConvertor.INSTANCE.registerTypeConvertor(T, typeConvertor);

            log.info(
                    "Register type convertor {} for type {}",
                    clazz.getSimpleName(),
                    T.getSimpleName()
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

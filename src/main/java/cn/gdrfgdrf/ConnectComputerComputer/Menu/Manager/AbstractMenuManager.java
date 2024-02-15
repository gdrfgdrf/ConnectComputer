package cn.gdrfgdrf.ConnectComputerComputer.Menu.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Interceptor.MenuMethodInterceptor;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Annotation.MenuClass;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Menu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.MenuNavigator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public abstract class AbstractMenuManager {
    protected void initMenu() {
        initAnnotationMenu();
    }

    private void initAnnotationMenu() {
        MenuClass menuClass = getClass().getAnnotation(MenuClass.class);
        if (menuClass == null) {
            return;
        }

        Class<? extends Menu>[] classes = menuClass.classes();
        String[] routes = menuClass.routes();

        for (int i = 0; i < classes.length; i++) {
            String route = routes[i];
            Class<? extends Menu> clazz = classes[i];

            initMenu(route, clazz);
        }
    }

    private void initMenu(String route, Class<? extends Menu> clazz) {
        try {
            Menu menu = MenuMethodInterceptor.INSTANCE.createInstance(clazz);

            MenuNavigator.INSTANCE.registerMenu(route, menu);

            log.info("Register menu {}", menu.getClass().getSimpleName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

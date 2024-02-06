package cn.gdrfgdrf.ConnectComputerComputer.Menu;

import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Network.Http.Base.MenuHttpNetworkRequest;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.PrintUtil;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

/**
 * @author gdrfgdrf
 */
@Slf4j
public enum MenuNavigator {
    INSTANCE;

    @Setter
    private MenuHttpNetworkRequest menuHttpNetworkRequest;

    private final Map<String, Menu> routeAndMenu = new HashMap<>();
    private final List<Menu> stack = new LinkedList<>();

    @Getter
    private Menu currentMenu;

    public void registerMenu(String route, Menu menu) {
        menu.setRoute(route);
        routeAndMenu.put(route, menu);
    }

    public Menu getMenu(String route) {
        return routeAndMenu.get(route);
    }

    public void popupAgain() {
        popupAgain(null);
    }

    public void popupAgain(Object args) {
        log.info("Dismiss menu " + currentMenu.getClass().getSimpleName());

        try {
            currentMenu.dismiss();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        log.info("Popup menu " + currentMenu.getClass().getSimpleName() + " again");
        try {
            currentMenu.setPopupOnDismissArgument(args);
            currentMenu.popup(args, menuHttpNetworkRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void popup(String route) {
        popup(route, null);
    }

    public void popup(String route, Object args) {
        Menu menu = routeAndMenu.get(route);
        if (menu == null) {
            throw new NullPointerException("Menu is not found by route " + route);
        }
        if (menu == currentMenu) {
            return;
        }
        if (currentMenu != null) {
            log.info("Dismiss menu " + currentMenu.getClass().getSimpleName());

            try {
                currentMenu.dismiss();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
        stack.add(menu);

        log.info("Popup menu " + menu.getClass().getSimpleName());
        PrintUtil.print(
                "\n{}{}{}",
                AppLocale.MENU_TITLE_PREFIX,
                menu.getTitle(),
                AppLocale.MENU_TITLE_SUFFIX
        );
        try {
            currentMenu = menu;
            menu.setPopupOnDismissArgument(args);
            menu.popup(args, menuHttpNetworkRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void dismiss() {
        if (currentMenu == null) {
            return;
        }

        int index = stack.lastIndexOf(currentMenu);
        if (index == 0) {
            return;
        }

        log.info("Dismiss menu " + currentMenu.getClass().getSimpleName());

        try {
            currentMenu.dismiss();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        stack.remove(index);
        index--;

        Menu up = stack.get(index);
        log.info("Popup menu " + up.getClass().getSimpleName());

        try {
            currentMenu = up;
            up.popup(up.getPopupOnDismissArgument(), menuHttpNetworkRequest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

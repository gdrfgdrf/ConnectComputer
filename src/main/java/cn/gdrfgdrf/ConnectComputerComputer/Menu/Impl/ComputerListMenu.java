package cn.gdrfgdrf.ConnectComputerComputer.Menu.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.Common.Network.Http.HttpNetworkRequest;
import cn.gdrfgdrf.ConnectComputerComputer.Common.User.User;
import cn.gdrfgdrf.ConnectComputerComputer.Common.Network.Http.Enum.HttpSiteEnum;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Computer.ComputerInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Route.MenuRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Menu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.MenuNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Annotation.Validated;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Impl.IntegerPositiveValidator;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Impl.NotNullValidator;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Impl.OnlyIntegerValidator;
import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.TableConvertor;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.PrintUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.LinkedList;
import java.util.List;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class ComputerListMenu extends Menu {
    private final List<ComputerInformation> computerInformationList = new LinkedList<>();

    @Override
    public String getTitle() {
        return AppLocale.MENU_TITLE_COMPUTER_LIST;
    }

    @Override
    public void popup(Object args) throws Exception {
        HttpNetworkRequest.INSTANCE.request(
                HttpSiteEnum.GET_COMPUTER_LIST,
                User.INSTANCE.getId()
        );

        computerInformationList.clear();
        computerInformationList.addAll(User.INSTANCE.getComputerList());
        String result = TableConvertor.convert(
                computerInformationList,
                AppLocale.COMPUTER_LIST_EMPTY,
                AppLocale.COMPUTER_NAME,
                AppLocale.COMPUTER_IS_ONLINE
        );

        PrintUtil.print(result);
    }

    @Override
    public void dismiss() throws Exception {
        computerInformationList.clear();
    }

    @Validated(validator = {
            NotNullValidator.class,
            OnlyIntegerValidator.class,
            IntegerPositiveValidator.class
    })
    @Override
    public void userInput(String input) throws Exception {
        if (computerInformationList.isEmpty()) {
            log.info(AppLocale.COMPUTER_LIST_EMPTY_TIP);
            return;
        }
        int index = Integer.parseInt(input);
        if (index >= computerInformationList.size()) {
            log.error(AppLocale.COMPUTER_NOT_FOUND);
            return;
        }

        ComputerInformation computerInformation = computerInformationList.get(index);
        if (computerInformation == null) {
            log.error(AppLocale.COMPUTER_NOT_FOUND);
            return;
        }

        MenuNavigator.INSTANCE.popup(MenuRoute.MENU_ROUTE_COMPUTER_DETAIL, computerInformation);
    }
}

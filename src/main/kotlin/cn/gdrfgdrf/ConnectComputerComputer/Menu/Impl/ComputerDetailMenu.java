package cn.gdrfgdrf.ConnectComputerComputer.Menu.Impl;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Computer.ComputerInformation;
import cn.gdrfgdrf.ConnectComputerComputer.Global.MenuRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Network.Http.Base.MenuHttpNetworkRequest;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Menu;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Operation.Impl.EnterMenuOperation;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Operation.OperationExecutor;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Annotation.Validated;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Impl.NotNullValidator;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Validation.Impl.OnlyIntegerValidator;
import cn.gdrfgdrf.ConnectComputerComputer.TableConvertor.TableConvertor;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.PrintUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class ComputerDetailMenu extends Menu {
    private final OperationExecutor operationExecutor = OperationExecutor.create(
            new EnterMenuOperation(MenuRoute.MENU_ROUTE_COMPUTER_CONTROL)
    );
    private ComputerInformation computerInformation;

    @Override
    public String getTitle() {
        return AppLocale.MENU_TITLE_COMPUTER_DETAIL;
    }

    @Validated(validator = {
            NotNullValidator.class
    })
    @Override
    public void popup(Object args, MenuHttpNetworkRequest request) throws Exception {
        computerInformation = (ComputerInformation) args;
        String result = TableConvertor.convert(
                computerInformation,
                AppLocale.COMPUTER_NAME,
                AppLocale.COMPUTER_IS_ONLINE
        );
        PrintUtil.print(result);

        operationExecutor.printIndex();
    }

    @Override
    public void dismiss() throws Exception {

    }

    @Validated(validator = {OnlyIntegerValidator.class})
    @Override
    public void userInput(String input) throws Exception {
        int operationIndex = Integer.parseInt(input);
        operationExecutor.execute(operationIndex, computerInformation);
    }
}

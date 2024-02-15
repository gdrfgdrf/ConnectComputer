package cn.gdrfgdrf.ConnectComputerComputer.Menu.Operation;

import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.MenuNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Operation.Base.Operation;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.PrintUtil;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class OperationExecutor {
    private final Operation[] operations;

    public static OperationExecutor create(Operation... operations) {
        return new OperationExecutor(operations);
    }

    private OperationExecutor(Operation... operations) {
        this.operations = operations;
    }

    public void printIndex() {
        PrintUtil.print(AppLocale.OPERATION_ENTER_INDEX);
        PrintUtil.print("{}: {}", 0, AppLocale.OPERATION_PREVIOUS_MENU);
        for (int i = 0; i < operations.length; i++) {
            PrintUtil.print("{}: {}", i + 1, operations[i].getShownName());
        }
    }

    public void execute(int index) {
        execute(index, null);
    }

    public void execute(int index, Object args) {
        if (index == 0) {
            MenuNavigator.INSTANCE.dismiss();
            return;
        }
        if (index - 1 < operations.length) {
            operations[index - 1].execute(args);
            return;
        }
        log.error(AppLocale.OPERATION_INDEX_OUT_OF_LIMITED);
    }
}

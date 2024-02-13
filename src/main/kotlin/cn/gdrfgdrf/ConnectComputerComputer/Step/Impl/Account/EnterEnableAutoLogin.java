package cn.gdrfgdrf.ConnectComputerComputer.Step.Impl.Account;

import cn.gdrfgdrf.ConnectComputerComputer.Common.User.User;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.Account;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Route.StepRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;
import cn.gdrfgdrf.ConnectComputerComputer.Step.StepNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class EnterEnableAutoLogin implements Step {
    private Account account;

    @Override
    public void start(DataStore dataStore) throws Exception {
        account = dataStore.getAccount();
        if (StringUtils.isBlank(User.INSTANCE.getUsername())) {
            account.setUsername(null);
            account.setPassword(null);
            StepNavigator.INSTANCE.startStep(StepRoute.STEP_ROUTE_ENTER_ACCOUNT_USERNAME);
            return;
        }
        if (account.isAutoLogin() != null) {
            StepNavigator.INSTANCE.nextStep();
            return;
        }

        log.info(AppLocale.WANT_TO_ENABLE_AUTO_LOGIN);
    }

    @Override
    public void userInput(String input) throws Exception {
        if (!"1".equals(input) && !"2".equals(input)) {
            log.error(AppLocale.ENTER_ERROR);
            StepNavigator.INSTANCE.startCurrentStepAgain();
            return;
        }

        if (account != null) {
            account.setAutoLogin("1".equals(input));
            StepNavigator.INSTANCE.nextStep();
        }
    }
}

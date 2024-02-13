package cn.gdrfgdrf.ConnectComputerComputer.Step.Impl.Account;

import cn.gdrfgdrf.ConnectComputerComputer.App;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.Account;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Route.StepRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;
import cn.gdrfgdrf.ConnectComputerComputer.Step.StepNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class EnterAccountPasswordStep implements Step {
    private Account account;

    @Override
    public void start(DataStore dataStore) throws Exception {
        account = dataStore.getAccount();
        if (GlobalConfiguration.SERVER_PUBLIC_KEY == null) {
            StepNavigator.INSTANCE.startStep(StepRoute.STEP_ROUTE_ENTER_SERVER_PROTOCOL);
            return;
        }
        if (!StringUtils.isBlank(account.getPassword())) {
            if (App.INSTANCE.isUnencryptedPassword()) {
                account.setPassword(
                        RSAUtils.publicEncrypt(
                                account.getPassword(),
                                GlobalConfiguration.SERVER_PUBLIC_KEY
                        ).toString()
                );
            }
            StepNavigator.INSTANCE.nextStep();
            return;
        }

        log.info(AppLocale.ENTER_PASSWORD);
    }

    @Override
    public void userInput(String input) throws Exception {
        if (account != null) {
            App.INSTANCE.setUnencryptedPassword(true);
            account.setPassword(
                    RSAUtils.publicEncrypt(
                            input,
                            GlobalConfiguration.SERVER_PUBLIC_KEY
                    ).toString()
            );
            StepNavigator.INSTANCE.nextStep();
        }
    }
}

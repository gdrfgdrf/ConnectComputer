/*
 * Copyright (C) 2024 Contributors
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <https://www.gnu.org/licenses/>.
 */

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
public class EnterControllerOrBeControlledStep implements Step {
    private DataStore dataStore;
    private Account account;

    @Override
    public void start(DataStore dataStore) throws Exception {
        this.dataStore = dataStore;
        account = dataStore.getAccount();
        if (StringUtils.isBlank(User.INSTANCE.getUsername())) {
            account.setUsername(null);
            account.setPassword(null);
            StepNavigator.INSTANCE.startStep(StepRoute.STEP_ROUTE_ENTER_ACCOUNT_USERNAME);
            return;
        }
        if (account.isAutoLogin() == null) {
            StepNavigator.INSTANCE.startStep(StepRoute.STEP_ROUTE_ENTER_ENABLE_AUTO_LOGIN);
            return;
        }
        if (account.isController() != null) {
            StepNavigator.INSTANCE.nextStep();
            return;
        }

        log.info(AppLocale.CONTROLLER_OR_BE_CONTROLLED);
    }

    @Override
    public void userInput(String input) throws Exception {
        if (!"1".equals(input) && !"2".equals(input)) {
            log.error(AppLocale.ENTER_ERROR);
            StepNavigator.INSTANCE.startCurrentStepAgain();
            return;
        }

        if (dataStore != null &&
                account != null) {
            account.setController("1".equals(input));
            if (account.isController()) {
                log.info(AppLocale.BE_CONTROLLER);
            } else {
                log.info(AppLocale.BE_CONTROLLED);
            }

            if (account.isAutoLogin()) {
                dataStore.saveDataBean(account);
            } else {
                String tempUsername = account.getUsername();
                String tempPassword = account.getPassword();

                account.setUsername(null);
                account.setPassword(null);
                dataStore.saveDataBean(account);

                account.setUsername(tempUsername);
                account.setPassword(tempPassword);
            }

            StepNavigator.INSTANCE.nextStep();
        }
    }
}

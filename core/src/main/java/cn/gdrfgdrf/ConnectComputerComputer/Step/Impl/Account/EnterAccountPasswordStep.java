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

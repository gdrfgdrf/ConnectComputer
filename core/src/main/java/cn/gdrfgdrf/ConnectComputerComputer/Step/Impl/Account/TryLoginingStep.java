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

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult;
import cn.gdrfgdrf.ConnectComputerComputer.Common.Network.Http.Enum.HttpSiteEnum;
import cn.gdrfgdrf.ConnectComputerComputer.Common.Network.Http.HttpNetworkRequest;
import cn.gdrfgdrf.ConnectComputerComputer.Common.User.User;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.Account;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Route.StepRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;
import cn.gdrfgdrf.ConnectComputerComputer.Step.StepNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AppUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class TryLoginingStep implements Step {
    @Override
    public void start(DataStore dataStore) throws Exception {
        Account account = dataStore.getAccount();
        if (account.getUsername() == null ||
                account.getPassword() == null) {
            account.setUsername(null);
            account.setPassword(null);
            StepNavigator.INSTANCE.startStep(StepRoute.STEP_ROUTE_ENTER_ACCOUNT_USERNAME);
            return;
        }

        log.info(AppLocale.TRY_LOGINING);
        ParseResult parseResult = (ParseResult) HttpNetworkRequest.INSTANCE.request(
                HttpSiteEnum.LOGIN,
                account.getUsername(),
                account.getPassword()
        );
        if (parseResult == null || !parseResult.isSuccess()) {
            if (parseResult != null) {
                log.error(parseResult.getMessage());
            }
            StepNavigator.INSTANCE.stepFailed();
            return;
        }

        log.info(parseResult.getMessage());
        log.info(AppLocale.LOGIN_ACCOUNT, User.INSTANCE.getUsername());

        StepNavigator.INSTANCE.nextStep();
    }

    @Override
    public void userInput(String input) throws Exception {

    }
}

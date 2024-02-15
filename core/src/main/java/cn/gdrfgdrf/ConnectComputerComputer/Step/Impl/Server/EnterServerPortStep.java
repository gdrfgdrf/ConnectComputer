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

package cn.gdrfgdrf.ConnectComputerComputer.Step.Impl.Server;

import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.ServerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;
import cn.gdrfgdrf.ConnectComputerComputer.Step.StepNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.NetworkUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class EnterServerPortStep implements Step {
    private ServerInfo serverInfo;

    @Override
    public void start(DataStore dataStore) throws Exception {
        serverInfo = dataStore.getServerInfo();
        if (NetworkUtils.isValidPort(serverInfo.getPort())) {
            StepNavigator.INSTANCE.nextStep();
            return;
        }
        log.info(AppLocale.ENTER_SERVER_PORT);
    }

    @Override
    public void userInput(String input) throws Exception {
        if (!NetworkUtils.isValidPort(input)) {
            log.error(AppLocale.ENTER_ERROR);
            StepNavigator.INSTANCE.startCurrentStepAgain();
            return;
        }

        if (serverInfo != null) {
            serverInfo.setPort(Integer.parseInt(input));
            StepNavigator.INSTANCE.nextStep();
        }
    }
}

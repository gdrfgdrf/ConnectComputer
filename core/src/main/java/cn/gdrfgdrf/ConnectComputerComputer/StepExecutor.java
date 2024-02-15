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

package cn.gdrfgdrf.ConnectComputerComputer;

import cn.gdrfgdrf.ConnectComputerComputer.CLI.CLITerminal;
import cn.gdrfgdrf.ConnectComputerComputer.CLI.Exception.ApplicationClosedException;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Route.StepRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Callback.StepChainCallback;
import cn.gdrfgdrf.ConnectComputerComputer.Step.StepNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Thread.ThreadPoolService;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AppUtils;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gdrfgdrf
 */
public class StepExecutor {
    @Setter
    private DataStore dataStore;
    @Setter
    private CLITerminal cliTerminal;
    @Getter
    private final Object lock = new Object();

    public void start() {
        StepNavigator.INSTANCE.setDataStore(dataStore);
        StepNavigator.INSTANCE.setStepChainCallback(callback);
        StepNavigator.INSTANCE.startStep(StepRoute.STEP_ROUTE_ENTER_SERVER_PROTOCOL);
        ThreadPoolService.newTask(readTerminal);
    }

    private final StepChainCallback callback = new StepChainCallback() {
        @Override
        public void onAllFinished() {
            try {
                cliTerminal.interruptReadLine();
            } catch (Exception e) {
                e.printStackTrace();
            }
            synchronized (lock) {
                lock.notifyAll();
            }
        }

        @Override
        public void onFailed() {
            AppUtils.exitProgram();
        }
    };
    private final Runnable readTerminal = () -> {
        while (true) {
            try {
                String line = cliTerminal.readLine(Constants.PROMPT);

                Step currentStep = StepNavigator.INSTANCE.getCurrentStep();
                if (currentStep == null) {
                    return;
                }
                currentStep.userInput(line);
            } catch (ApplicationClosedException ignored) {
                AppUtils.exitProgram();
                break;
            } catch (InterruptedException ignored) {
                break;
            } catch (Exception e) {
                e.printStackTrace();
                AppUtils.exitProgram();
                break;
            }
        }
    };
}

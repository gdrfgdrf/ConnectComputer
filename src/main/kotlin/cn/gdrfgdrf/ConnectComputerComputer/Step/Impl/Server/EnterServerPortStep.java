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

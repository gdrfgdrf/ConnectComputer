package cn.gdrfgdrf.ConnectComputerComputer.Step.Impl.Server;

import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.ServerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;
import cn.gdrfgdrf.ConnectComputerComputer.Step.StepNavigator;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class EnterServerProtocolStep implements Step {
    private ServerInfo serverInfo;

    @Override
    public void start(DataStore dataStore) throws Exception {
        serverInfo = dataStore.getServerInfo();
        if (serverInfo.getSslEnabled() != null) {
            StepNavigator.INSTANCE.nextStep();
            return;
        }

        log.info(AppLocale.ENTER_PROTOCOL);
    }

    @Override
    public void userInput(String input) throws Exception {
        if (!"1".equals(input) && !"2".equals(input)) {
            log.error(AppLocale.ENTER_ERROR);
            StepNavigator.INSTANCE.startCurrentStepAgain();
            return;
        }

        if (serverInfo != null) {
            serverInfo.setSslEnabled(!"1".equals(input));
            StepNavigator.INSTANCE.nextStep();
        }
    }
}

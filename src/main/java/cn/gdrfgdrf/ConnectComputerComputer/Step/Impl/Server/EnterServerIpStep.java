package cn.gdrfgdrf.ConnectComputerComputer.Step.Impl.Server;

import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.ServerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;
import cn.gdrfgdrf.ConnectComputerComputer.Step.StepNavigator;
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils;
import lombok.extern.slf4j.Slf4j;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class EnterServerIpStep implements Step {
    private ServerInfo serverInfo;

    @Override
    public void start(DataStore dataStore) throws Exception {
        serverInfo = dataStore.getServerInfo();
        if (!StringUtils.isBlank(serverInfo.getIp())) {
            StepNavigator.INSTANCE.nextStep();
            return;
        }
        log.info(AppLocale.ENTER_SERVER_IP);
    }

    @Override
    public void userInput(String input) throws Exception {
        if (serverInfo != null) {
            serverInfo.setIp(input);
            StepNavigator.INSTANCE.nextStep();
        }
    }
}

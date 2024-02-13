package cn.gdrfgdrf.ConnectComputerComputer.Step.Impl.Server;

import cn.gdrfgdrf.ConnectComputerComputer.App;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult;
import cn.gdrfgdrf.ConnectComputerComputer.Common.Network.Http.Enum.HttpSiteEnum;
import cn.gdrfgdrf.ConnectComputerComputer.Common.Network.Http.HttpNetworkRequest;
import cn.gdrfgdrf.ConnectComputerComputer.Data.Bean.ServerInfo;
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Route.StepRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Base.Step;
import cn.gdrfgdrf.ConnectComputerComputer.Step.StepNavigator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ThreadUtils;

import java.time.Duration;

/**
 * @author gdrfgdrf
 */
@Slf4j
public class CheckServerConnectableStep implements Step {
    @Override
    public void start(DataStore dataStore) throws Exception {
        ServerInfo serverInfo = dataStore.getServerInfo();
        if (serverInfo.getSslEnabled() == null &&
                serverInfo.getIp() == null &&
                serverInfo.getPort() == null) {
            serverInfo.reset();
            StepNavigator.INSTANCE.startStep(StepRoute.STEP_ROUTE_ENTER_SERVER_PROTOCOL);
            return;
        }

        log.info(AppLocale.CHECK_SERVER_CONNECTABLE);
        ParseResult parseResult = (ParseResult) HttpNetworkRequest.INSTANCE.request(HttpSiteEnum.AVAILABLE);
        if (parseResult == null ||
                !parseResult.isSuccess()) {
            if (!App.INSTANCE.isSilent()) {
                serverInfo.reset();
                StepNavigator.INSTANCE.startStep(StepRoute.STEP_ROUTE_ENTER_SERVER_PROTOCOL);
            } else {
                log.error(
                        AppLocale.NETWORK_ERROR_SILENCE,
                        Constants.NETWORK_ERROR_SLEEP / 1000
                );
                ThreadUtils.sleepQuietly(Duration.ofMillis(Constants.NETWORK_ERROR_SLEEP));
                StepNavigator.INSTANCE.startCurrentStepAgain();
            }

            return;
        }

        dataStore.saveDataBean(serverInfo);

        log.info(AppLocale.SERVER_CONNECTABLE);
        StepNavigator.INSTANCE.nextStep();
    }

    @Override
    public void userInput(String input) throws Exception {

    }
}

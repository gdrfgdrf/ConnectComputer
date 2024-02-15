package cn.gdrfgdrf.ConnectComputerComputer.Step.Impl.Server;

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
public class TryGettingPublicKeyStep implements Step {
    @Override
    public void start(DataStore dataStore) throws Exception {
        ServerInfo serverInfo = dataStore.getServerInfo();
        if (serverInfo.getSslEnabled() == null ||
                serverInfo.getIp() == null ||
                serverInfo.getPort() == null) {
            serverInfo.reset();
            StepNavigator.INSTANCE.startStep(StepRoute.STEP_ROUTE_ENTER_SERVER_PROTOCOL);
            return;
        }

        log.info(AppLocale.TRY_GETTING_PUBLIC_KEY);

        ParseResult parseResult = (ParseResult) HttpNetworkRequest.INSTANCE.request(HttpSiteEnum.GET_PUBLIC_KEY);
        if (parseResult == null || !parseResult.isSuccess()) {
            log.error(
                    AppLocale.GETTING_PUBLIC_KEY_ERROR,
                    Constants.GET_PUBLIC_KEY_ERROR_SLEEP_TIME / 1000
            );
            ThreadUtils.sleep(Duration.ofMillis(Constants.GET_PUBLIC_KEY_ERROR_SLEEP_TIME));
            StepNavigator.INSTANCE.startCurrentStepAgain();
            return;
        }

        log.info(AppLocale.GETTING_PUBLIC_KEY_SUCCESS);
        StepNavigator.INSTANCE.nextStep();
    }

    @Override
    public void userInput(String input) throws Exception {

    }
}

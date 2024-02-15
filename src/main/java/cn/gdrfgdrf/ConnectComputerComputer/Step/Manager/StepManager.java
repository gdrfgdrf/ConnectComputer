package cn.gdrfgdrf.ConnectComputerComputer.Step.Manager;

import cn.gdrfgdrf.ConnectComputerComputer.Bean.Bean;
import cn.gdrfgdrf.ConnectComputerComputer.Global.Route.StepRoute;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Annotation.StepClass;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Impl.Account.*;
import cn.gdrfgdrf.ConnectComputerComputer.Step.Impl.Server.*;

/**
 * @author gdrfgdrf
 */
@StepClass(classes = {
        EnterServerProtocolStep.class,
        EnterServerIpStep.class,
        EnterServerPortStep.class,
        CheckServerConnectableStep.class,
        TryGettingPublicKeyStep.class,

        EnterAccountUsernameStep.class,
        EnterAccountPasswordStep.class,
        TryLoginingStep.class,
        EnterEnableAutoLogin.class,
        EnterControllerOrBeControlledStep.class
},
        routes = {
                StepRoute.STEP_ROUTE_ENTER_SERVER_PROTOCOL,
                StepRoute.STEP_ROUTE_ENTER_SERVER_IP,
                StepRoute.STEP_ROUTE_ENTER_SERVER_PORT,
                StepRoute.STEP_ROUTE_CHECK_SERVER_CONNECTABLE,
                StepRoute.STEP_ROUTE_TRY_GETTING_PUBLIC_KEY,

                StepRoute.STEP_ROUTE_ENTER_ACCOUNT_USERNAME,
                StepRoute.STEP_ROUTE_ENTER_ACCOUNT_PASSWORD,
                StepRoute.STEP_ROUTE_TRY_LOGINING,
                StepRoute.STEP_ROUTE_ENTER_ENABLE_AUTO_LOGIN,
                StepRoute.STEP_ROUTE_ENTER_CONTROLLER_OR_BE_CONTROLLED
        })
public class StepManager extends AbstractStepManager implements Bean {
    @Override
    public void init() throws Exception {
        super.initStep();
    }
}

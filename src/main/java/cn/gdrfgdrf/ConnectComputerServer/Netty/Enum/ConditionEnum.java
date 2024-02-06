package cn.gdrfgdrf.ConnectComputerServer.Netty.Enum;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Condition.Condition;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Condition.Impl.*;
import lombok.Getter;

/**
 * @author gdrfgdrf
 */
@Getter
public enum ConditionEnum {
    AES_KEY_HAS_BEEN_GENERATED(new AesKeyHasBeenGeneratedCondition(), false),
    LOGGED_IN(new NeedLoginCondition(), false),
    LOGIN_MODE_IS_COMPUTER(new LoginModeIsComputerCondition(), false),
    LOGIN_MODE_IS_CONTROLLER(new LoginModeIsControllerUserCondition(), false),

    CONTROLLER_IS_CONTROLLING(new ControllerIsControllingCondition(), false),
    COMPUTER_IS_CONTROLLED(new ComputerIsControlledCondition(), false),

    CONTROLLER_EXCHANGED_CONNECTION_RSA_KEY(new ControllerExchangedConnectionRsaKeyCondition(), false),
    COMPUTER_EXCHANGED_CONNECTION_RSA_KEY(new ComputerExchangedConnectionRsaKeyCondition(),false),
    ;

    private final Condition impl;
    private final boolean close;

    ConditionEnum(Condition impl, boolean close) {
        this.impl = impl;
        this.close = close;
    }
}

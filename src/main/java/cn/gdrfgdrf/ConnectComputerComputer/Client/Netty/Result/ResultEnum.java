package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Result;

import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import lombok.Getter;
import lombok.ToString;

/**
 * @author gdrfgdrf
 */
@ToString
public enum ResultEnum {
    NETTY_AES_KEY_EXCHANGED(Constants.SUCCESS, AppLocale.NETTY_AES_KEY_EXCHANGED);

    @Getter
    private final int code;
    @Getter
    private final String message;

    ResultEnum(int code, String message) {
        this.code = code;
        this.message = message;
    }
}

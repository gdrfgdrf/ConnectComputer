package cn.gdrfgdrf.ConnectComputerServer.Exception;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * @author gdrfgdrf
 */
public class IllegalParameterException extends RuntimeException {
    @Getter
    private final ResultEnum resultEnum;
    @Getter
    private final String[] placeholders;

    @Setter
    @Getter
    private Information information;

    public IllegalParameterException(ResultEnum resultEnum) {
        super(resultEnum.getMessageEnum().toString());
        this.resultEnum = resultEnum;
        this.placeholders = null;
    }

    public IllegalParameterException(ResultEnum resultEnum, String... placeholders) {
        super(resultEnum.getMessageEnum().toString());
        this.resultEnum = resultEnum;
        this.placeholders = placeholders;
    }
}

package cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Exception;

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.Base.CustomException;
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale;
import com.google.protobuf.Message;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

/**
 * @author gdrfgdrf
 */
@AllArgsConstructor
public class NettyNotFoundPacketHandlerException extends CustomException {
    @NonNull
    @Getter
    private final Message msg;

    @Override
    public String getErrorMessage() {
        return AppLocale.CUSTOM_EXCEPTION_NETTY_NOT_FOUND_PACKET_HANDLER_ERROR;
    }
}

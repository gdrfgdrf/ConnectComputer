package cn.gdrfgdrf.ConnectComputerServer.Netty.Exception;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.Protobuf.Common.InternalErrorProto;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import com.google.protobuf.GeneratedMessageV3;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;

/**
 * @author gdrfgdrf
 */
public class IllegalParameterNettyException extends IllegalParameterException {
    @Getter
    private GeneratedMessageV3 packet;
    @Getter
    private String requestId;
    @Setter
    @Getter
    private boolean close;

    public IllegalParameterNettyException(ResultEnum resultEnum, Class<? extends GeneratedMessageV3> packetClass, String requestId, boolean close) {
        super(resultEnum);

        try {
            Method newBuilder = packetClass.getMethod("newBuilder");
            GeneratedMessageV3.Builder<?> builder = (GeneratedMessageV3.Builder<?>) newBuilder.invoke(null);
            this.packet = (GeneratedMessageV3) builder.build();
        } catch (Exception e) {
            e.printStackTrace();
            this.packet = InternalErrorProto.ErrorPacket.newBuilder().build();
        }
        this.requestId = requestId;
        this.close = close;
    }

    @Override
    public void setInformation(Information information) {

    }

    @Override
    public Information getInformation() {
        return null;
    }
}
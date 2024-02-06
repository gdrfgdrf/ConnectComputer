package cn.gdrfgdrf.ConnectComputerServer.AOP;

import cn.gdrfgdrf.ConnectComputerServer.Netty.Exception.IllegalParameterNettyException;
import cn.gdrfgdrf.Protobuf.BaseProto;
import cn.gdrfgdrf.Protobuf.Common.ExternalErrorProto;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Base.Validator;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Enum.ValidationEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.PacketValidationCollection;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import com.google.protobuf.Message;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import java.lang.reflect.Method;

/**
 * @author gdrfgdrf
 */
@Aspect
@Component
public class NettyPacketArgumentValidateAOP {

    @Around("within(cn.gdrfgdrf.ConnectComputerServer.Netty.Base.PacketHandler+)")
    public Object doAround(ProceedingJoinPoint point) throws Throwable {
        Object[] args = point.getArgs();
        if (args.length < 2) {
            return point.proceed(point.getArgs());
        }

        BaseProto.Packet packet = (BaseProto.Packet) args[1];
        Message message = (Message) args[2];
        PacketValidationCollection.PacketValidation packetValidation =
                PacketValidationCollection.INSTANCE.getPacketValidation(message.getClass());

        if (packetValidation != null && !validate(message, packetValidation)) {
            throw new IllegalParameterNettyException(
                    ResultEnum.ERROR_PARAMETER,
                    ExternalErrorProto.ParameterErrorPacket.class,
                    packet.getRequestId(),
                    packetValidation.isCloseIfFailed()
            );
        }
        return point.proceed(point.getArgs());
    }

    private boolean validate(Message packet, PacketValidationCollection.PacketValidation packetValidation) throws Exception {
        Method[] methods = packetValidation.getMethod();
        ValidationEnum[][] validationEnums = packetValidation.getValidationEnum();
        Object[][] objects = packetValidation.getArgs();

        for (int i = 0; i < methods.length; i++) {
            Method method = methods[i];
            ValidationEnum[] validationEnum = validationEnums[i];
            Object[] args = null;

            if (objects != null) {
                args = objects[i];
            }

            for (int j = 0; j < validationEnum.length; j++) {
                ValidationEnum validation = validationEnum[j];
                Validator validator = validation.getValidator();

                Object argument = method.invoke(packet);
                boolean result = validator.validate(argument, args != null ? args[j] : null);

                if (!result) {
                    return false;
                }
            }
        }
        return true;
    }

}

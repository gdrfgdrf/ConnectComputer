package cn.gdrfgdrf.ConnectComputerServer.Netty.Validation;

import cn.gdrfgdrf.Protobuf.Action.Account.AccountProto;
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto;
import cn.gdrfgdrf.ConnectComputerServer.Netty.Validation.Enum.ValidationEnum;
import cn.gdrfgdrf.Protobuf.Action.Controller.ControllerProto;
import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author gdrfgdrf
 */
public enum PacketValidationCollection {
    INSTANCE;

    private final Map<Class<? extends GeneratedMessageV3>, PacketValidation> MAP = new HashMap<>();

    public PacketValidation getPacketValidation(Class<? extends Message> clazz) {
        return MAP.get(clazz);
    }

    private Method getMethod(Class<? extends GeneratedMessageV3> clazz, String methodName) {
        try {
            return clazz.getMethod(methodName);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Getter
    public static class PacketValidation {
        private final Method[] method;
        private final ValidationEnum[][] validationEnum;
        @Setter
        private boolean closeIfFailed = false;
        @Setter
        private Object[][] args;

        public PacketValidation(Method[] method, ValidationEnum[][] validationEnum) {
            this.method = method;
            this.validationEnum = validationEnum;
        }

        public PacketValidation(Method[] method, ValidationEnum[][] validationEnum, boolean closeIfFailed) {
            this.method = method;
            this.validationEnum = validationEnum;
            this.closeIfFailed = closeIfFailed;
        }

        public PacketValidation(Method[] method, ValidationEnum[][] validationEnum, Object[][] args) {
            this.method = method;
            this.validationEnum = validationEnum;
            this.args = args;
        }
    }

    {
        MAP.put(AccountProto.UserLoginPacket.class, new PacketValidation(
                new Method[]{
                        getMethod(AccountProto.UserLoginPacket.class, "getToken"),
                        getMethod(AccountProto.UserLoginPacket.class, "getLoginMode")
                },
                new ValidationEnum[][]{
                        new ValidationEnum[]{ValidationEnum.STRING_NOT_BLANK},
                        new ValidationEnum[]{ValidationEnum.NOT_NULL}
                },
                true
        ));

        MAP.put(ComputerProto.RegisterComputerPacket.class, new PacketValidation(
                new Method[]{
                        getMethod(ComputerProto.RegisterComputerPacket.class, "getName"),
                },
                new ValidationEnum[][]{
                        new ValidationEnum[]{ValidationEnum.STRING_NOT_BLANK, ValidationEnum.STRING_LENGTH_NOT_OUT_OF_LIMITED}
                },
                new Object[][]{
                        new Object[]{null, 255}
                }
        ));

        MAP.put(ControllerProto.ConnectComputerPacket.class, new PacketValidation(
                new Method[]{
                        getMethod(ControllerProto.ConnectComputerPacket.class, "getId"),
                },
                new ValidationEnum[][]{
                        new ValidationEnum[]{ValidationEnum.NOT_NULL, ValidationEnum.INTEGER_IS_POSITIVE}
                }
        ));
    }

}

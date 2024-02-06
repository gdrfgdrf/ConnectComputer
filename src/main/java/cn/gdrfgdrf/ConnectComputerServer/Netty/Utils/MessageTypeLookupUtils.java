package cn.gdrfgdrf.ConnectComputerServer.Netty.Utils;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;

import java.lang.reflect.InvocationTargetException;
import java.util.Set;

/**
 * @author gdrfgdrf
 */
public class MessageTypeLookupUtils {
    public static MessageTypeLookup getMessageTypeLookup() {
        Set<Class<? extends GeneratedMessageV3>>
                classes = ClassScanner.lookupClasses(GeneratedMessageV3.class);
        return generateMessageTypeLookup(classes);
    }

    public static MessageTypeLookup getMessageTypeLookup(String... messageBasePackages) {
        Set<Class<? extends GeneratedMessageV3>>
                classes = ClassScanner.lookupClasses(GeneratedMessageV3.class, messageBasePackages);
        return generateMessageTypeLookup(classes);
    }

    public static MessageTypeLookup getMessageTypeLookup(Class<?>... messageBasePackageClasses) {
        Set<Class<? extends GeneratedMessageV3>>
                classes = ClassScanner.lookupClasses(GeneratedMessageV3.class, messageBasePackageClasses);
        return generateMessageTypeLookup(classes);
    }

    private static MessageTypeLookup generateMessageTypeLookup(Set<Class<? extends GeneratedMessageV3>> classes) {
        MessageTypeLookup.Builder messageTypeLookupBuilder = MessageTypeLookup.newBuilder();
        try {
            for (Class<? extends GeneratedMessageV3> clazz : classes) {
                Message.Builder builder = (Message.Builder) clazz.getMethod("newBuilder").invoke(null);
                Message messageV3 = builder.build();
                messageTypeLookupBuilder.addMessageTypeMapping(messageV3.getDescriptorForType(), clazz);
            }
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
        return messageTypeLookupBuilder.build();
    }
}

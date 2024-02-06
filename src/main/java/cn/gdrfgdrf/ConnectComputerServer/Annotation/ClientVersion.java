package cn.gdrfgdrf.ConnectComputerServer.Annotation;

import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author gdrfgdrf
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface ClientVersion {
    VersionEnum version();

    @Target({ElementType.METHOD})
    @Retention(RetentionPolicy.RUNTIME)
    @interface Exclude {

    }
}

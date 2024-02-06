package cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Common;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import cn.gdrfgdrf.ConnectComputerServer.Enum.RSAKeyEnum;
import lombok.AccessLevel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class KeyInformation extends Information {
    @Getter(AccessLevel.PRIVATE)
    public static final KeyInformation INSTANCE = new KeyInformation();

    private final String publicKey = Base64.encodeBase64String(RSAKeyEnum.HTTP_KEY.getPublicKey().getEncoded());
}

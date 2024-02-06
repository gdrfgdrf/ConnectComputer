package cn.gdrfgdrf.ConnectComputerServer.Enum;

import lombok.Getter;
import lombok.Setter;

import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * @author gdrfgdrf
 */

public enum RSAKeyEnum {
    TOKEN_KEY,
    HTTP_KEY,
    NETTY_KEY;

    @Setter
    @Getter
    private PrivateKey privateKey;

    @Setter
    @Getter
    private PublicKey publicKey;

    RSAKeyEnum() {
    }
}

package cn.gdrfgdrf.ConnectComputerServer.Netty.Common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author gdrfgdrf
 */
@AllArgsConstructor
public class AesKey {
    @Getter
    @NonNull
    private IvParameterSpec iv;
    @Getter
    @NonNull
    private SecretKey key;
}

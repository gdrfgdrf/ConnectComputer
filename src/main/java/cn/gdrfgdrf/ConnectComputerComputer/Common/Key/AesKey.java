package cn.gdrfgdrf.ConnectComputerComputer.Common.Key;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.ToString;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

/**
 * @author gdrfgdrf
 */
@AllArgsConstructor
@ToString
public class AesKey {
    @Getter
    @NonNull
    private IvParameterSpec iv;
    @Getter
    @NonNull
    private SecretKey key;
}


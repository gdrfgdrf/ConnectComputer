package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Common;

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class KeyInformation extends Information {
    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }
}

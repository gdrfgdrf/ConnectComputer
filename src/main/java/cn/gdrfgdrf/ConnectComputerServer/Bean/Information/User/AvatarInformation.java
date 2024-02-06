package cn.gdrfgdrf.ConnectComputerServer.Bean.Information.User;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AvatarInformation extends Information {
    private String sha256;
}

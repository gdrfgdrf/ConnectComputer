package cn.gdrfgdrf.ConnectComputerServer.Bean.Information.User;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Enum.RSAKeyEnum;
import cn.gdrfgdrf.ConnectComputerServer.Netty.NettyServer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserSecretInformation extends UserInformation {
    private String username;
    private String token;

    private final Integer nettyServerPort = NettyServer.PORT;

    public static UserSecretInformation createByUser(UserEntity user, String token) {
        UserSecretInformation userSecretInformation = new UserSecretInformation();
        userSecretInformation.setId(user.getId());
        userSecretInformation.setUsername(user.getUsername());
        userSecretInformation.setDisplayName(user.getDisplayName());
        userSecretInformation.setUserGroup(user.getUserGroup());
        userSecretInformation.setToken(token);

        return userSecretInformation;
    }
}

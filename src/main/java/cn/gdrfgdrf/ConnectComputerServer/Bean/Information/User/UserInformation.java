package cn.gdrfgdrf.ConnectComputerServer.Bean.Information.User;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Enum.UserGroupEnum;
import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Information;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserInformation extends Information {
    private Integer id;
    private String displayName;
    private UserGroupEnum userGroup;

    public static UserInformation createByUser(UserEntity user) {
        UserInformation userInformation = new UserInformation();
        userInformation.setId(user.getId());
        userInformation.setDisplayName(user.getDisplayName());
        userInformation.setUserGroup(user.getUserGroup());

        return userInformation;
    }
}

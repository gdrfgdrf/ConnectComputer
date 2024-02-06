package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.User;

import cn.gdrfgdrf.ConnectComputerComputer.Common.User.UserGroupEnum;
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Information;
import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@ToString
public class UserInformation extends Information {
    private Integer id;
    private String displayName;
    private UserGroupEnum userGroup;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public UserGroupEnum getUserGroup() {
        return userGroup;
    }

    public void setUserGroup(UserGroupEnum userGroup) {
        this.userGroup = userGroup;
    }
}

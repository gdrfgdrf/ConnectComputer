package cn.gdrfgdrf.ConnectComputerServer.Bean.POJO;

import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Group.ValidationGroup;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Annotation.NotBlankAndMessage;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * @author gdrfgdrf
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserWithChangePasswordEntity extends UserEntity implements Serializable {
    @NotBlankAndMessage(overrideMessageEnum = MessageEnum.ERROR_ORIGINAL_PASSWORD_CANNOT_BE_NULL, groups = ValidationGroup.StringNotBlankOriginalPassword.class)
    private String originalPassword;
}

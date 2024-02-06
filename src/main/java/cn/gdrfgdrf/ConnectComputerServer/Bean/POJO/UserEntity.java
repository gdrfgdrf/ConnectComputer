package cn.gdrfgdrf.ConnectComputerServer.Bean.POJO;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Enum.UserGroupEnum;
import cn.gdrfgdrf.ConnectComputerServer.Global.Global;
import cn.gdrfgdrf.ConnectComputerServer.Result.MessageEnum;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Annotation.*;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Group.ValidationGroup;
import com.baomidou.mybatisplus.annotation.*;
import com.github.dreamyoung.mprelation.JoinColumn;
import com.github.dreamyoung.mprelation.Lazy;
import com.github.dreamyoung.mprelation.OneToMany;
import lombok.Data;
import org.apache.ibatis.type.EnumTypeHandler;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

/**
 * @author gdrfgdrf
 */
@Data
@TableName("user")
public class UserEntity implements Serializable {
    @TableId(value = "id", type = IdType.AUTO)
    @NotNullAndMessage(overrideMessageEnum = MessageEnum.ERROR_ID_CANNOT_BE_NULL, groups = ValidationGroup.IntegerNotNullId.class)
    @PositiveIntegerAndMessage(overrideMessageEnum = MessageEnum.ERROR_ID_ILLEGAL, groups = ValidationGroup.IntegerPositiveId.class)
    private Integer id;

    @TableField("username")
    @NotBlankAndMessage(overrideMessageEnum = MessageEnum.ERROR_USERNAME_CANNOT_BE_NULL, groups = ValidationGroup.StringNotBlankUsername.class)
    @MaxAndMessage(overrideMessageEnum = MessageEnum.ERROR_USERNAME_OUT_OF_LIMITED, value = 255, groups = ValidationGroup.StringMaxLengthUsername.class)
    @PatternAndMessage(overrideMessageEnum = MessageEnum.ERROR_USERNAME_ILLEGAL, regexp = Global.USERNAME_REGEX, groups = ValidationGroup.StringPatternUsername.class)
    private String username;

    @TableField("displayName")
    @NotBlankAndMessage(overrideMessageEnum = MessageEnum.ERROR_DISPLAY_NAME_ILLEGAL, groups = ValidationGroup.StringNotBlankDisplayName.class)
    @MaxAndMessage(overrideMessageEnum = MessageEnum.ERROR_DISPLAY_NAME_OUT_OF_LIMITED, value = 18, groups = ValidationGroup.StringMaxLengthDisplayName.class)
    private String displayName;

    @TableField("password")
    @NotBlankAndMessage(overrideMessageEnum = MessageEnum.ERROR_PASSWORD_CANNOT_BE_NULL, groups = ValidationGroup.StringNotBlankPassword.class)
    private String password;

    @TableField("salt")
    private String salt;

    @TableField(value = "userGroup", typeHandler = EnumTypeHandler.class)
    private UserGroupEnum userGroup;

    @TableField(value = "avatarUrl", insertStrategy = FieldStrategy.ALWAYS, updateStrategy = FieldStrategy.ALWAYS)
    private String avatarUrl;

    @TableField(exist = false)
    @OneToMany
    @JoinColumn(name = "id", referencedColumnName = "userId")
    @Lazy(value = false)
    private List<TokenEntity> tokenEntities = new LinkedList<>();

    public boolean containsToken(String token) {
        return tokenEntities.stream().anyMatch(tokenEntity -> tokenEntity.getContent().equals(token));
    }
}

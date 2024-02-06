package cn.gdrfgdrf.ConnectComputerServer.Controller;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.ClientVersion;
import cn.gdrfgdrf.ConnectComputerServer.Annotation.NeedToken;
import cn.gdrfgdrf.ConnectComputerServer.Annotation.PassToken;
import cn.gdrfgdrf.ConnectComputerServer.Annotation.SecurityParameter;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserWithChangePasswordEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserWithMultipartFileEntity;
import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import cn.gdrfgdrf.ConnectComputerServer.Service.IUserService;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Group.ValidationGroup;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Annotation.OnlyIntegerAndMessage;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author gdrfgdrf
 */
@RestController
@RequestMapping("/user")
@Validated
@ClientVersion(version = VersionEnum.v1_0_0)
public class UserController {
    private final IUserService userService;

    @Autowired
    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @PassToken
    @SecurityParameter
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Result login(@Validated({
            ValidationGroup.StringNotBlankUsername.class,
            ValidationGroup.StringMaxLengthUsername.class,
            ValidationGroup.StringPatternUsername.class,
            ValidationGroup.StringNotBlankPassword.class,
    }) @RequestBody UserEntity tempUser) throws Exception {
        return userService.login(tempUser);
    }

    @NeedToken
    @SecurityParameter
    @RequestMapping(value = "/update-login", method = RequestMethod.POST)
    public Result updateLogin(@Validated({
            ValidationGroup.IntegerNotNullId.class,
            ValidationGroup.IntegerPositiveId.class,
    }) @RequestBody UserEntity tempUser) throws Exception {
        return userService.updateLogin(tempUser);
    }

    @NeedToken
    @SecurityParameter
    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public Result logout(@Validated({
            ValidationGroup.IntegerNotNullId.class,
            ValidationGroup.IntegerPositiveId.class,
    }) @RequestBody UserEntity tempUser) throws Exception {
        return userService.logout(tempUser);
    }

    @NeedToken
    @SecurityParameter
    @RequestMapping(value = "/password", method = RequestMethod.PUT)
    public Result changePassword(@Validated({
            ValidationGroup.IntegerNotNullId.class,
            ValidationGroup.IntegerPositiveId.class,
            ValidationGroup.StringNotBlankOriginalPassword.class,
            ValidationGroup.StringNotBlankPassword.class,
    }) @RequestBody UserWithChangePasswordEntity tempUser) throws Exception {
        return userService.changePassword(tempUser);
    }

    @ClientVersion.Exclude
    @PassToken
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public Result userInfo(@OnlyIntegerAndMessage @PathVariable String id) throws Exception {
        return userService.userInfo(id);
    }

    @NeedToken
    @SecurityParameter
    @RequestMapping(value = "/avatar", method = RequestMethod.POST)
    public Result changeAvatar(@Validated({
            ValidationGroup.IntegerNotNullId.class,
            ValidationGroup.IntegerPositiveId.class,
    }) @RequestBody UserWithMultipartFileEntity userWithMultipartFileEntity) throws Exception {
        return userService.changeAvatar(userWithMultipartFileEntity, userWithMultipartFileEntity.getMultipartFile());
    }

    @PassToken
    @RequestMapping(value = "/{id}/avatar", method = RequestMethod.GET)
    public void getAvatar(@OnlyIntegerAndMessage @PathVariable String id, HttpServletResponse response) throws Exception {
        userService.getAvatar(id, response);
    }

    @ClientVersion.Exclude
    @PassToken
    @RequestMapping(value = "/{id}/avatar/sha256", method = RequestMethod.GET)
    public Result getAvatarSha256(@OnlyIntegerAndMessage @PathVariable String id) throws Exception {
        return userService.getAvatarSha256(id);
    }

    @NeedToken
    @SecurityParameter
    @RequestMapping(value = "/display-name", method = RequestMethod.POST)
    public Result changeDisplayName(@Validated({
            ValidationGroup.IntegerNotNullId.class,
            ValidationGroup.IntegerPositiveId.class,
            ValidationGroup.StringNotBlankDisplayName.class,
            ValidationGroup.StringMaxLengthDisplayName.class
    }) @RequestBody UserEntity tempUser) throws Exception {
        return userService.changeDisplayName(tempUser);
    }
}

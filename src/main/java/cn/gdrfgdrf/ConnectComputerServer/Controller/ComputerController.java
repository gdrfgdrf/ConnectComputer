package cn.gdrfgdrf.ConnectComputerServer.Controller;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.ClientVersion;
import cn.gdrfgdrf.ConnectComputerServer.Annotation.NeedToken;
import cn.gdrfgdrf.ConnectComputerServer.Annotation.SecurityParameter;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Enum.VersionEnum;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import cn.gdrfgdrf.ConnectComputerServer.Service.IComputerService;
import cn.gdrfgdrf.ConnectComputerServer.Validation.Group.ValidationGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gdrfgdrf
 */
@RestController
@RequestMapping("/computer")
@Validated
@ClientVersion(version = VersionEnum.v1_0_0)
public class ComputerController {
    private final IComputerService computerService;

    @Autowired
    public ComputerController(IComputerService computerService) {
        this.computerService = computerService;
    }

    @NeedToken
    @SecurityParameter
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public Result getComputerList(@Validated({
            ValidationGroup.IntegerNotNullId.class,
            ValidationGroup.IntegerPositiveId.class,
    }) @RequestBody UserEntity tempUser) {
        return computerService.getComputerListByUser(tempUser);
    }
}

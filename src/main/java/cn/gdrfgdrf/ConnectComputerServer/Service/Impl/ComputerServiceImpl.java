package cn.gdrfgdrf.ConnectComputerServer.Service.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Computer.ComputerListInformation;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.ComputerEntity;
import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Exception.IllegalParameterException;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IComputerEntityService;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUserEntityService;
import cn.gdrfgdrf.ConnectComputerServer.Service.IComputerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author gdrfgdrf
 */
@Service
public class ComputerServiceImpl implements IComputerService {
    private final IUserEntityService userEntityService;
    private final IComputerEntityService computerEntityService;

    @Autowired
    public ComputerServiceImpl(
            IUserEntityService userEntityService,
            IComputerEntityService computerEntityService
    ) {
        this.userEntityService = userEntityService;
        this.computerEntityService = computerEntityService;
    }

    @Override
    public Result getComputerListByUser(UserEntity tempUser) {
        UserEntity user = userEntityService.getById(tempUser.getId());

        if (user != null) {
            List<ComputerEntity> computerEntities = computerEntityService.listByOwnerId(tempUser.getId());
            ComputerListInformation computerListInformation = ComputerListInformation.createByComputerList(computerEntities);

            Result result = new Result().setFromResultEnum(ResultEnum.SUCCESS_GET);
            result.addData(computerListInformation);

            return result;
        } else {
            throw new IllegalParameterException(ResultEnum.ERROR_NOT_FOUND_USER);
        }
    }
}


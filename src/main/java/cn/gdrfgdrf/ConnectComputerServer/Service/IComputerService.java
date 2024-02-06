package cn.gdrfgdrf.ConnectComputerServer.Service;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;

/**
 * @author gdrfgdrf
 */
public interface IComputerService {
    Result getComputerListByUser(UserEntity tempUser);
}

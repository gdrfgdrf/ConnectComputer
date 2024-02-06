package cn.gdrfgdrf.ConnectComputerServer.Service.Database;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import com.github.dreamyoung.mprelation.IService;

/**
 * @author gdrfgdrf
 */
public interface IUserEntityService extends IService<UserEntity> {
    UserEntity findByUsername(String username);
}

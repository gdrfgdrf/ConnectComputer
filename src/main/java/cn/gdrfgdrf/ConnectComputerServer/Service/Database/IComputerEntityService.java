package cn.gdrfgdrf.ConnectComputerServer.Service.Database;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.ComputerEntity;
import com.github.dreamyoung.mprelation.IService;

import java.util.List;

/**
 * @author gdrfgdrf
 */
public interface IComputerEntityService extends IService<ComputerEntity> {
    ComputerEntity findByOwnerAndComputerId(Integer ownerId, Integer computerId);
    List<ComputerEntity> listByOwnerId(Integer ownerId);
}

package cn.gdrfgdrf.ConnectComputerServer.Service.Database.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.ComputerEntity;
import cn.gdrfgdrf.ConnectComputerServer.Mapper.ComputerEntityMapper;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IComputerEntityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.dreamyoung.mprelation.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author gdrfgdrf
 */
@Service
public class ComputerEntityServiceImpl extends ServiceImpl<ComputerEntityMapper, ComputerEntity> implements IComputerEntityService {

    @Transactional
    @Override
    public ComputerEntity findByOwnerAndComputerId(Integer ownerId, Integer computerId) {
        QueryWrapper<ComputerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ownerId", ownerId);
        queryWrapper.eq("id", computerId);

        return getOne(queryWrapper);
    }

    @Transactional
    @Override
    public List<ComputerEntity> listByOwnerId(Integer ownerId) {
        QueryWrapper<ComputerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("ownerId", ownerId);

        return list(queryWrapper);
    }
}

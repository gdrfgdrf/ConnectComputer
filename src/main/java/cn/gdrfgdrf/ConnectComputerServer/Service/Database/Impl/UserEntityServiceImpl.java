package cn.gdrfgdrf.ConnectComputerServer.Service.Database.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.UserEntity;
import cn.gdrfgdrf.ConnectComputerServer.Mapper.UserEntityMapper;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.IUserEntityService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.dreamyoung.mprelation.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author gdrfgdrf
 */
@Service
public class UserEntityServiceImpl extends ServiceImpl<UserEntityMapper, UserEntity> implements IUserEntityService {
    @Transactional
    @Override
    public UserEntity findByUsername(String username) {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("username", username);

        return getOne(queryWrapper);
    }
}

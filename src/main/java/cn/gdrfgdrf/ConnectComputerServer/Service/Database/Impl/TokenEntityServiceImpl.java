package cn.gdrfgdrf.ConnectComputerServer.Service.Database.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Bean.POJO.TokenEntity;
import cn.gdrfgdrf.ConnectComputerServer.Mapper.TokenEntityMapper;
import cn.gdrfgdrf.ConnectComputerServer.Service.Database.ITokenEntityService;
import com.github.dreamyoung.mprelation.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * @author gdrfgdrf
 */
@Service
public class TokenEntityServiceImpl extends ServiceImpl<TokenEntityMapper, TokenEntity> implements ITokenEntityService {
}

package cn.gdrfgdrf.ConnectComputerServer.Service.Impl;

import cn.gdrfgdrf.ConnectComputerServer.Bean.Information.Common.KeyInformation;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import cn.gdrfgdrf.ConnectComputerServer.Result.ResultEnum;
import cn.gdrfgdrf.ConnectComputerServer.Service.IServerInfoService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

/**
 * @author gdrfgdrf
 */
@Service
public class ServerInfoServiceImpl implements IServerInfoService {
    @Override
    public Result isAvailable() {
        Result result = new Result();
        result.setFromResultEnum(ResultEnum.AVAILABLE_SERVER);

        return result;
    }

    @Cacheable(value = "server_info", key = "'serverInfo'")
    @Override
    public Result getPublicKey() throws Exception {
        Result result = new Result();

        result.setFromResultEnum(ResultEnum.SUCCESS_GET);
        result.addData(KeyInformation.INSTANCE);

        return result;
    }
}

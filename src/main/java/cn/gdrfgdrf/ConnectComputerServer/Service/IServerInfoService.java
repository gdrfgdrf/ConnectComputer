package cn.gdrfgdrf.ConnectComputerServer.Service;

import cn.gdrfgdrf.ConnectComputerServer.Result.Result;

/**
 * @author gdrfgdrf
 */
public interface IServerInfoService {
    Result isAvailable() throws Exception;
    Result getPublicKey() throws Exception;
}

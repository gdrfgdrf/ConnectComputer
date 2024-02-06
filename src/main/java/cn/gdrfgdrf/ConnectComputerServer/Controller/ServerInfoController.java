package cn.gdrfgdrf.ConnectComputerServer.Controller;

import cn.gdrfgdrf.ConnectComputerServer.Annotation.PassToken;
import cn.gdrfgdrf.ConnectComputerServer.Annotation.SecurityParameter;
import cn.gdrfgdrf.ConnectComputerServer.Result.Result;
import cn.gdrfgdrf.ConnectComputerServer.Service.IServerInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author gdrfgdrf
 */
@RestController
@RequestMapping("/info")
public class ServerInfoController {
    private final IServerInfoService serverInfoService;

    @Autowired
    public ServerInfoController(IServerInfoService serverInfoService) {
        this.serverInfoService = serverInfoService;
    }

    @PassToken
    @SecurityParameter(responseEncode = false, parameterDecode = false)
    @RequestMapping(value = "/available", method = RequestMethod.GET)
    public Result isAvailable() throws Exception {
        return serverInfoService.isAvailable();
    }

    @PassToken
    @SecurityParameter(responseEncode = false, parameterDecode = false)
    @RequestMapping(value = "/public-key", method = RequestMethod.GET)
    public Result getPublicKey() throws Exception {
        return serverInfoService.getPublicKey();
    }
}

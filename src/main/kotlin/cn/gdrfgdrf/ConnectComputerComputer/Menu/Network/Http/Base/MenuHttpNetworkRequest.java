package cn.gdrfgdrf.ConnectComputerComputer.Menu.Network.Http.Base;

import cn.gdrfgdrf.ConnectComputerComputer.Menu.Network.Http.Enum.HttpSiteEnum;

/**
 * @author gdrfgdrf
 */
public interface MenuHttpNetworkRequest {
    Object request(HttpSiteEnum site, Object... args);
}

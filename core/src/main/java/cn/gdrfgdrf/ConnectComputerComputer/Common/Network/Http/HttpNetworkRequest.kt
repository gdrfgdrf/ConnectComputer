package cn.gdrfgdrf.ConnectComputerComputer.Common.Network.Http

import cn.gdrfgdrf.ConnectComputerComputer.Common.Network.Http.Enum.HttpSiteEnum
import cn.gdrfgdrf.ConnectComputerComputer.Extension.launchIO
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.runBlocking
import java.util.Arrays

object HttpNetworkRequest {
    private val coroutine = MainScope()

    fun request(site: HttpSiteEnum, vararg args: Any?): Any? {
        var result: Any? = null

        runBlocking {
            coroutine.launchIO {
                result = site.execute(args);
            }.join()
        }

        return result
    }
}
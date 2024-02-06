package cn.gdrfgdrf.ConnectComputerComputer.Menu.Network.Http.Enum

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request.HttpNetwork

enum class HttpSiteEnum(private val callback: suspend (Array<Any?>) -> Any?) {
    GET_COMPUTER_LIST(callback = {
        HttpNetwork.getComputerList(it[0] as Int)
    });

    suspend fun execute(args: Array<Any?>): Any? {
        return callback(args)
    }
}
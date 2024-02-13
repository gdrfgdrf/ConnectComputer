package cn.gdrfgdrf.ConnectComputerComputer.Common.Network.Http.Enum

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request.HttpNetwork

enum class HttpSiteEnum(private val callback: suspend (Array<out Any?>) -> Any?) {
    AVAILABLE(callback = {
       HttpNetwork.isAvailable()
    }),
    GET_PUBLIC_KEY(callback = {
        HttpNetwork.getPublicKey()
    }),
    LOGIN(callback = {
        HttpNetwork.login(it[0] as String, it[1] as String)
    }),
    GET_COMPUTER_LIST(callback = {
        HttpNetwork.getComputerList(it[0] as Int)
    });

    suspend fun execute(args: Array<out Any?>): Any? {
        return callback(args)
    }
}
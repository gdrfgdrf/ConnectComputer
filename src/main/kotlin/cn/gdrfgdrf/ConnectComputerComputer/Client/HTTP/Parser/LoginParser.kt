package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser

import cn.gdrfgdrf.ConnectComputerComputer.Common.User.User
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.User.UserSecretInformation
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration
import okhttp3.ResponseBody

/**
 * @author gdrfgdrf
 */
object LoginParser : BaseParser<ParseResult>() {
    override suspend fun parse(responseBody: ResponseBody, args: Any?): ParseResult {
        val result = super.baseParse(responseBody)

        if (result.success) {
            val data = result.getInformation(UserSecretInformation::class.java)

            val id = data.id
            val username = data.username
            val displayName = data.displayName
            val userGroup = data.userGroup
            val token = data.token

            val nettyServerPort = data.nettyServerPort

            GlobalConfiguration.NETTY_SERVER_PORT = nettyServerPort

            User.id = id
            User.username = username
            User.displayName = displayName
            User.userGroup = userGroup
            User.token = token
        }

        return result
    }
}
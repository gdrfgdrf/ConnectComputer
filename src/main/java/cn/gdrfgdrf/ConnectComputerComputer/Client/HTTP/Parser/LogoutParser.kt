package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser

import cn.gdrfgdrf.ConnectComputerComputer.Common.User.User
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult
import okhttp3.ResponseBody

/**
 * @author gdrfgdrf
 */
object LogoutParser : BaseParser<ParseResult>() {
    override suspend fun parse(responseBody: ResponseBody, args: Any?): ParseResult {
        val baseBody = super.baseParse(responseBody)

        if (baseBody.isSuccess) {
            User.id = null
            User.username = null
            User.displayName = null
            User.userGroup = null
            User.token = null
        }

        return baseBody
    }
}
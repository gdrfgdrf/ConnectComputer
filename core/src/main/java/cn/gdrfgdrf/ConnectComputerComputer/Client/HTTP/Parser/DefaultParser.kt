package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult
import okhttp3.ResponseBody

/**
 * @author gdrfgdrf
 */
object DefaultParser : BaseParser<ParseResult>() {
    override suspend fun parse(responseBody: ResponseBody, args: Any?): ParseResult {
        return super.baseParse(responseBody)
    }

}
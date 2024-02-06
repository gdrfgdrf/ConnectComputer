package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser

import cn.gdrfgdrf.ConnectComputerComputer.Common.User.User
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Computer.ComputerListInformation
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult
import okhttp3.ResponseBody
import org.slf4j.Logger
import org.slf4j.LoggerFactory

/**
 * @author gdrfgdrf
 */
object ComputerListParser : BaseParser<ParseResult>() {
    private val log: Logger = LoggerFactory.getLogger(ComputerListParser::class.java)

    override suspend fun parse(responseBody: ResponseBody, args: Any?): ParseResult {
        val baseBody = super.baseParse(responseBody)

        if (baseBody.success) {
            val data = baseBody.getInformation(ComputerListInformation::class.java)

            User.computerList.clear()
            data.list.forEach { computerInformation ->
                User.computerList.add(computerInformation)
            }
        }

        return baseBody
    }
}
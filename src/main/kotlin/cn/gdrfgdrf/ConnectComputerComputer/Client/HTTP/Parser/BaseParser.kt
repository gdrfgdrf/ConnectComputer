package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.Exception.JsonNullException
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.Exception.NotAJsonObjectException
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.Exception.ParseException
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult
import com.alibaba.fastjson2.JSON
import com.alibaba.fastjson2.JSONObject
import okhttp3.ResponseBody
import okio.BufferedSource
import java.nio.charset.Charset

/**
 * @author gdrfgdrf
 */
abstract class BaseParser<T> {
    @Throws(ParseException::class)
    protected fun baseParse(responseBody: ResponseBody): ParseResult {
        return try {
            val body = responseBodyToString(responseBody)

            val rawJson = JSONObject.parseObject(body)

            val parseResult = JSON.parseObject(rawJson.toString(), ParseResult::class.java)
            parseResult.rawJson = rawJson

            parseResult
        } catch (e: Exception) {
            throw ParseException(e)
        }
    }

    private fun responseBodyToString(responseBody: ResponseBody): String {
        return bufferedSourceToString(responseBody.source())
    }

    private fun bufferedSourceToString(source: BufferedSource): String {
        source.request(Long.MAX_VALUE)
        val buffer = source.buffer

        return buffer.clone().readString(Charset.defaultCharset())
    }

    abstract suspend fun parse(responseBody: ResponseBody, args: Any?): T
}

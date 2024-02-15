package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.*
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.URL.URLEnum
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser.Exception.ParseException
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult
import okhttp3.Request

/**
 * @author gdrfgdrf
 */
object HttpNetwork {
    suspend fun isAvailable(): ParseResult {
        return NetworkRequest<Nothing>(URLEnum.IS_AVAILABLE) {

        }.executeAndParsingWith(DefaultParser)
    }

    suspend fun getPublicKey(): ParseResult {
        return NetworkRequest<Nothing>(URLEnum.GET_PUBLIC_KEY) {

        }.executeAndParsingWith(GetPublicKeyParser)
    }

    suspend fun login(
        username: String,
        password: String
    ): ParseResult {


        return NetworkRequest<Nothing>(URLEnum.LOGIN) {
            jsonBody {
                put("username", username)
                put("password", password)
            }
        }.executeAndParsingWith(LoginParser)
    }

    suspend fun changePassword(
        id: Int,
        originalPassword: String,
        newPassword: String
    ): ParseResult {
        return NetworkRequest<Nothing>(URLEnum.CHANGE_PASSWORD) {
            jsonBody(URLEnum.CHANGE_PASSWORD.method.method) {
                put("id", id)
                put("originalPassword", originalPassword)
                put("password", newPassword)
            }
        }.executeAndParsingWith(ChangePasswordParser)
    }

    suspend fun logout(id: Int): ParseResult {
        return NetworkRequest<Nothing>(URLEnum.LOGOUT) {
            jsonBody {
                put("id", id)
            }
        }.executeAndParsingWith(LogoutParser)
    }

    suspend fun getComputerList(id: Int): ParseResult {
        return NetworkRequest<Nothing>(URLEnum.GET_COMPUTER_LIST) {
            jsonBody {
                put("id", id)
            }
        }.executeAndParsingWith(ComputerListParser)
    }
}

private suspend inline fun <T> Request.executeAndParsingWith(
    block: BaseParser<T>,
    args: Any? = null
): T {
    return execute {
        try {
            block.parse(body, args)
        } catch (e: Exception) {
            throw ParseException(e)
        }
    }
}
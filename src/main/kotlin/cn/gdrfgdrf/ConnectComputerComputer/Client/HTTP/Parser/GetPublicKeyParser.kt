package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Common.KeyInformation
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils
import okhttp3.ResponseBody
import java.security.PublicKey

/**
 * @author gdrfgdrf
 */
object GetPublicKeyParser : BaseParser<ParseResult>() {

    override suspend fun parse(responseBody: ResponseBody, args: Any?): ParseResult {
        val baseResult = super.baseParse(responseBody)

        if (baseResult.success) {
            val information = baseResult.getInformation(KeyInformation::class.java)
            val key = information.publicKey
            val publicKey: PublicKey = RSAUtils.getPublicKey(key)

            GlobalConfiguration.SERVER_PUBLIC_KEY = publicKey

            Constants.VERSION_ENCRYPTED = RSAUtils.publicEncrypt(
                Constants.VERSION,
                GlobalConfiguration.SERVER_PUBLIC_KEY
            ).toString()
        }

        return baseResult
    }

}
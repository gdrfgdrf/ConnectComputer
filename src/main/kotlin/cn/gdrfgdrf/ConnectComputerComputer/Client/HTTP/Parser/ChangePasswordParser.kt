package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Parser

import cn.gdrfgdrf.ConnectComputerComputer.Common.User.User
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore
import cn.gdrfgdrf.ConnectComputerComputer.Bean.BeanManager
import okhttp3.ResponseBody

/**
 * @author gdrfgdrf
 */
object ChangePasswordParser : BaseParser<ParseResult>() {
    override suspend fun parse(responseBody: ResponseBody, args: Any?): ParseResult {
        val baseBody = super.baseParse(responseBody)

        if (baseBody.isSuccess) {
            val dataStore: DataStore = BeanManager.getInstance().getBean("DataStore")
            val account = dataStore.account

            account.username = null
            account.password = null
            account.isAutoLogin = null

            dataStore.saveDataBean(account)

            User.id = null
            User.username = null
            User.displayName = null
            User.userGroup = null
        }

        return baseBody
    }
}
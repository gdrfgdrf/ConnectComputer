package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Handler

import java.text.MessageFormat

/**
 * @author gdrfgdrf
 */
class UserIdURLHandler :
    URLHandler<Int> {
    override fun handle(url: String, args: Array<out Int>): String {
        var result: String? = null

        args.forEach {
            result = MessageFormat.format(url, it)
        }
        if (result == null) {
            return url
        }

        return result!!
    }
}
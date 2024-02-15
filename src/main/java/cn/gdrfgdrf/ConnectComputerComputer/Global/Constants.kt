package cn.gdrfgdrf.ConnectComputerComputer.Global

/**
 * @author gdrfgdrf
 */
object Constants {
    const val PROMPT = ">"

    const val VERSION = "v1_0_0"
    @JvmField
    var VERSION_ENCRYPTED = ""

    const val DEBUG = true

    const val NETWORK_ERROR_SLEEP: Long = 10000
    const val GET_PUBLIC_KEY_ERROR_SLEEP_TIME: Long = 15000
    const val NETTY_SERVER_CONNECTION_ERROR_SLEEP_TIME: Long = 10000

    const val USERNAME_REGEX = "^[a-zA-Z0-9_-]{6,18}$"

    const val SUCCESS = 20000
}
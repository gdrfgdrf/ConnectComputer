package cn.gdrfgdrf.ConnectComputerComputer

import cn.gdrfgdrf.ConnectComputerComputer.CLI.Terminal
import cn.gdrfgdrf.ConnectComputerComputer.Common.User.User
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.ParseResult
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request.HttpNetwork
import cn.gdrfgdrf.ConnectComputerComputer.Data.DataStore
import cn.gdrfgdrf.ConnectComputerComputer.Extension.launchIO
import cn.gdrfgdrf.ConnectComputerComputer.Global.Constants
import cn.gdrfgdrf.ConnectComputerComputer.Global.GlobalConfiguration
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale
import cn.gdrfgdrf.ConnectComputerComputer.Menu.Network.Http.Base.MenuHttpNetworkRequest
import cn.gdrfgdrf.ConnectComputerComputer.Utils.AppUtils
import cn.gdrfgdrf.ConnectComputerComputer.Utils.NetworkUtils
import cn.gdrfgdrf.ConnectComputerComputer.Utils.RSAUtils
import cn.gdrfgdrf.ConnectComputerComputer.Utils.StringUtils
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.runBlocking
import org.apache.commons.lang3.ThreadUtils.sleep
import org.slf4j.Logger
import java.time.Duration
import java.util.concurrent.TimeUnit

/**
 * @author gdrfgdrf
 */
class AppKotlin(private val log: Logger, private val dataStore: DataStore, private val terminal: Terminal) {
    private val coroutine = MainScope()
    val menuHttpNetworkRequest =
        MenuHttpNetworkRequest { method, args ->
            var result: Any? = null

            runBlocking {
                coroutine.launchIO {
                    result = method.execute(args);
                }.join()
            }

            result
        }

    fun enterServerInfo() = runBlocking {
        val serverInfo = dataStore.serverInfo

        while (true) {
            if (serverInfo.sslEnabled == null) {
                log.info(AppLocale.ENTER_PROTOCOL)

                while (true) {
                    val protocol = terminal.readLine()
                    if ("1" == protocol || "2" == protocol) {
                        serverInfo.sslEnabled = "1" != protocol
                        break
                    } else {
                        log.error(AppLocale.ENTER_ERROR)
                    }
                }
            }

            if (StringUtils.isBlank(serverInfo.ip) ||
                !NetworkUtils.isValidPort(serverInfo.port)
            ) {
                log.info(AppLocale.ENTER_SERVER_IP)
                serverInfo.ip = terminal.readLine()

                log.info(AppLocale.ENTER_SERVER_PORT)
                while (true) {
                    val portStr = terminal.readLine()

                    if (NetworkUtils.isValidPort(portStr)) {
                        serverInfo.port = portStr.toInt()
                        break
                    } else {
                        log.error(AppLocale.ENTER_ERROR)
                    }
                }
            }

            while (true) {
                log.info(AppLocale.CHECK_SERVER_CONNECTABLE)

                var available: ParseResult? = null
                coroutine.launchIO {
                    available = HttpNetwork.isAvailable()
                }.join()

                if (available != null) {
                    dataStore.saveDataBean(serverInfo)

                    log.info(AppLocale.SERVER_CONNECTABLE)
                    return@runBlocking
                } else {
                    if (!App.INSTANCE.silent) {
                        serverInfo.sslEnabled = null
                        serverInfo.ip = null
                        serverInfo.port = null
                    }
                }

                if (!App.INSTANCE.silent) {
                    break
                } else {
                    log.error(
                        AppLocale.NETWORK_ERROR_SILENCE,
                        Constants.NETWORK_ERROR_SLEEP / 1000
                    )
                    sleep(Duration.ofMillis(Constants.NETWORK_ERROR_SLEEP))
                }
            }
        }
    }

    fun enterAccount(
        enterPassword: Boolean = false
    ) = runBlocking {
        val account = dataStore.account

        while (true) {
            var enterPassword = enterPassword

            if (StringUtils.isBlank(account.username) ||
                StringUtils.isBlank(account.password)) {
                log.info(AppLocale.ENTER_USERNAME)

                while (true) {
                    val username = terminal.readLine()
                    if (StringUtils.verifyByPattern(username, Constants.USERNAME_REGEX)) {
                        account.username = username
                        break
                    } else {
                        log.error(AppLocale.USERNAME_ILLEGAL)
                        log.error(AppLocale.ENTER_AGAIN)
                    }
                }

                log.info(AppLocale.ENTER_PASSWORD)
                account.password = terminal.readLine()
                enterPassword = true
            }

            while (true) {
                log.info(AppLocale.TRY_GETTING_PUBLIC_KEY)

                var publicKey: ParseResult? = null
                coroutine.launchIO {
                    publicKey = HttpNetwork.getPublicKey()
                }.join()
                if (publicKey?.success == false) {
                    log.error(
                        AppLocale.GETTING_PUBLIC_KEY_ERROR,
                        Constants.GET_PUBLIC_KEY_ERROR_SLEEP_TIME / 1000
                    )
                    sleep(Duration.ofMillis(Constants.GET_PUBLIC_KEY_ERROR_SLEEP_TIME))
                } else {
                    log.info(AppLocale.GETTING_PUBLIC_KEY_SUCCESS)
                    break
                }
            }
            if (enterPassword) {
                account.password = RSAUtils.publicEncrypt(
                    account.password,
                    GlobalConfiguration.SERVER_PUBLIC_KEY
                ).toString()
            }

            log.info(AppLocale.TRY_LOGINING)

            var login: ParseResult? = null
            coroutine.launchIO {
                login = HttpNetwork.login(
                    account.username,
                    account.password
                )
            }.join()

            if (login?.success == true) {
                log.info(login!!.message)
                log.info(AppLocale.LOGIN_ACCOUNT, User.username)

                if (account.autoLogin == null) {
                    log.info(AppLocale.WANT_TO_ENABLE_AUTO_LOGIN)

                    while (true) {
                        val enable = terminal.readLine()
                        if ("1" == enable || "2" == enable) {
                            account.autoLogin = "1" == enable
                            log.info(
                                if (account.autoLogin) {
                                    AppLocale.ENABLED_AUTO_LOGIN
                                } else {
                                    AppLocale.DISABLED_AUTO_LOGIN
                                }
                            )

                            break
                        } else {
                            log.error(AppLocale.ENTER_ERROR)
                        }
                    }
                }

                if (account.controller == null) {
                    log.info(AppLocale.CONTROLLER_OR_BE_CONTROLLED)

                    while (true) {
                        val controller = terminal.readLine()
                        if ("1" == controller || "2" == controller) {
                            account.controller = "1" == controller
                            break
                        } else {
                            log.error(AppLocale.ENTER_ERROR)
                        }
                    }
                }
                log.info(
                    if (account.controller) {
                        AppLocale.BE_CONTROLLER
                    } else {
                        AppLocale.BE_CONTROLLED
                    }
                )

                if (account.autoLogin) {
                    dataStore.saveDataBean(account)
                }

                break
            } else {
                log.error(login?.message)
                AppUtils.exitProgram()
            }
        }
    }
}
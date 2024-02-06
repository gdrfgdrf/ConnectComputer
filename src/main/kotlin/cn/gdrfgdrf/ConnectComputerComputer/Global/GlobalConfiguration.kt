package cn.gdrfgdrf.ConnectComputerComputer.Global

import cn.gdrfgdrf.ConnectComputerComputer.Common.Key.AesKey
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.Terminal
import java.security.PrivateKey
import java.security.PublicKey

/**
 * @author gdrfgdrf
 */
object GlobalConfiguration {
    lateinit var SERVER_PUBLIC_KEY: PublicKey

    @JvmField
    var NETTY_SERVER_PORT: Int = -1
    lateinit var aesKey: AesKey

    lateinit var tempOwnPrivateKey: PrivateKey
    lateinit var tempOwnPublicKey: PublicKey
    lateinit var tempPublicKey: PublicKey
    lateinit var tempAesKey: AesKey

    lateinit var terminal: Terminal
}
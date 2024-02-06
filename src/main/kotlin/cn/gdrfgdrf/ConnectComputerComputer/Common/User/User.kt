package cn.gdrfgdrf.ConnectComputerComputer.Common.User

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Result.Information.Computer.ComputerInformation
import java.io.Serializable

/**
 * @author gdrfgdrf
 */
object User : Serializable {
    var id: Int? = null
    var username: String? = null
    var displayName: String? = null
    var userGroup: UserGroupEnum? = null
    var token: String? = null

    val computerList: MutableList<ComputerInformation> = mutableListOf()
}
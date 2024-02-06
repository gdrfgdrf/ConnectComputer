package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request.Json

import com.alibaba.fastjson2.JSONObject

class JSONObjectBuilder {
    private val json = JSONObject()
    fun put(key: String, value: Any) = json.put(key, value)

    fun build(): String {
        return json.toString()
    }
}
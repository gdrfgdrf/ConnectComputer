package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request.Json

import cn.gdrfgdrf.ConnectComputerComputer.Utils.JacksonUtils
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.node.ArrayNode
import com.fasterxml.jackson.databind.node.ObjectNode
import com.fasterxml.jackson.databind.util.RawValue
import java.math.BigDecimal
import java.math.BigInteger

class ObjectNodeBuilder {
    private val json = JacksonUtils.newTree()

    fun putNull(key: String): ObjectNode = json.putNull(key)
    fun putPOJO(key: String, pojo: Any): ObjectNode = json.putPOJO(key, pojo)
    fun putObject(key: String): ObjectNode = json.putObject(key)
    fun putRawValue(key: String, rawValue: RawValue): ObjectNode = json.putRawValue(key, rawValue)
    fun putArray(key: String): ArrayNode = json.putArray(key)
    fun putIfAbsent(key: String, value: JsonNode): JsonNode = json.putIfAbsent(key, value)

    fun put(key: String, value: String): ObjectNode = json.put(key, value)
    fun put(key: String, value: Int): ObjectNode = json.put(key, value)
    fun put(key: String, value: Long): ObjectNode = json.put(key, value)
    fun put(key: String, value: Float): ObjectNode = json.put(key, value)
    fun put(key: String, value: Double): ObjectNode = json.put(key, value)
    fun put(key: String, value: Short): ObjectNode = json.put(key, value)
    fun put(key: String, value: Boolean): ObjectNode = json.put(key, value)
    fun put(key: String, value: ByteArray): ObjectNode = json.put(key, value)
    fun put(key: String, value: BigDecimal): ObjectNode = json.put(key, value)
    fun put(key: String, value: BigInteger): ObjectNode = json.put(key, value)

    fun build(): String {
        return json.toString()
    }
}
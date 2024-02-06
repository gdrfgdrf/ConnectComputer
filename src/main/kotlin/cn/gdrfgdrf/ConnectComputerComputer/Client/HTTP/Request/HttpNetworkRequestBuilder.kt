package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Extension.executeAsync
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request.Json.JSONObjectBuilder
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.URL.URLEnum
import cn.gdrfgdrf.ConnectComputerComputer.Utils.HttpUtils
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.nio.charset.Charset
import kotlin.contracts.ExperimentalContracts
import kotlin.contracts.InvocationKind
import kotlin.contracts.contract

/**
 * @author gdrfgdrf
 */
inline fun <T> NetworkRequest(
    url: URLEnum,
    vararg args: T,
    builder: Request.Builder.() -> Unit = {}
) = Request.Builder()
    .url(url.getFullURL(args))
    .apply {

    }
    .apply(
        builder
    )
    .build()

val MEDIA_TYPE_JSON: MediaType = "application/json; charset=${Charset.defaultCharset().name()}".toMediaType()
fun Request.Builder.jsonBody(
    method: String = "post",
    builder: JSONObjectBuilder.() -> Unit
) = method(
    method,
    buildJSONObject(builder)
        .toRequestBody(MEDIA_TYPE_JSON)
)

fun buildJSONObject(buildAction: JSONObjectBuilder.() -> Unit): String {
    val builder = JSONObjectBuilder()
    builder.buildAction()
    return builder.build()
}

@OptIn(ExperimentalContracts::class)
suspend inline fun <R> Request.execute(block: Response.() -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return HttpUtils.OK_HTTP_CLIENT.newCall(this)
        .executeAsync()
        .use(block)
}

package cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request

import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Extension.executeAsync
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Interceptor.PrepareAndCallRequestInterceptor
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.Request.Json.ObjectNodeBuilder
import cn.gdrfgdrf.ConnectComputerComputer.Client.HTTP.URL.URLEnum
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
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
    builder: ObjectNodeBuilder.() -> Unit
) = method(
    method,
    buildObjectNode(builder)
        .toRequestBody(MEDIA_TYPE_JSON)
)

fun buildObjectNode(buildAction: ObjectNodeBuilder.() -> Unit): String {
    val builder = ObjectNodeBuilder()
    builder.buildAction()
    return builder.build()
}

@OptIn(ExperimentalContracts::class)
suspend inline fun <R> Request.execute(block: Response.() -> R): R {
    contract {
        callsInPlace(block, InvocationKind.EXACTLY_ONCE)
    }
    return OK_HTTP_CLIENT.newCall(this)
        .executeAsync()
        .use(block)
}

val OK_HTTP_CLIENT: OkHttpClient = OkHttpClient.Builder()
    .addInterceptor(PrepareAndCallRequestInterceptor())
    .connectTimeout(5, TimeUnit.SECONDS)
    .writeTimeout(5, TimeUnit.SECONDS)
    .readTimeout(5, TimeUnit.SECONDS)
    .build()

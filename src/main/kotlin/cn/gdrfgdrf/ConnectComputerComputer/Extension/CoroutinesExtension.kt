package cn.gdrfgdrf.ConnectComputerComputer.Extension

import cn.gdrfgdrf.ConnectComputerComputer.ExceptionHandler.BetterUncaughtExceptionHandler
import cn.gdrfgdrf.ConnectComputerComputer.Language.AppLocale
import kotlinx.coroutines.*

/**
 * @author gdrfgdrf
 */

@DelicateCoroutinesApi
fun launchIO(block: suspend CoroutineScope.() -> Unit): Job =
    GlobalScope.launch(Dispatchers.IO, CoroutineStart.DEFAULT, block)

fun CoroutineScope.launchIO(
    errorMessage: String = AppLocale.CAUGHT_ERROR,
    block: suspend CoroutineScope.() -> Unit,
): Job {
    val job = launch(Dispatchers.IO) {
        runCatching {
            block()
        }.onFailure {
            (Thread.getDefaultUncaughtExceptionHandler() as BetterUncaughtExceptionHandler)
                .uncaughtException(Thread.currentThread(), it, errorMessage)
        }
    }

    return job
}
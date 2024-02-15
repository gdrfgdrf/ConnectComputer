package cn.gdrfgdrf.ConnectComputerComputer.Terminal.Connector

import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Callback.TerminalCharArrayResponseCallback
import com.jediterm.pty.PtyProcessTtyConnector
import com.pty4j.PtyProcess
import java.nio.charset.Charset

class RealtimeCallbackPtyProcessTtyConnector(
    process: PtyProcess,
    charset: Charset,
    val callback: TerminalCharArrayResponseCallback? = null
) :
    PtyProcessTtyConnector(process, charset) {

    override fun read(buf: CharArray, offset: Int, length: Int): Int {
        val result = super.read(buf, offset, length)
        callback?.let {
            if (offset > result) {
                return@let
            }
            val len = buf.copyOfRange(offset, result)
            callback.response(len)
        }

        return result
    }

}
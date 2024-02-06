package cn.gdrfgdrf.ConnectComputerComputer.Terminal.Connector

import cn.gdrfgdrf.ConnectComputerComputer.Client.Netty.Poster.SyncPacketPoster.SyncPacketPoster
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Base.NettyProcessTtyConnector
import cn.gdrfgdrf.ConnectComputerComputer.Terminal.Callback.TerminalCharArrayResponseCallback
import cn.gdrfgdrf.Protobuf.Action.Computer.ComputerProto.TerminalResponsePacket
import cn.gdrfgdrf.Protobuf.BaseProto.Packet
import com.google.protobuf.Message
import org.apache.commons.lang3.tuple.Pair
import java.io.StringReader

class RealtimeCallbackNettyProcessTtyConnector(
    val callback: TerminalCharArrayResponseCallback? = null
) :
    NettyProcessTtyConnector() {

    override fun read(buf: CharArray, offset: Int, length: Int): Int {
        var pair: Pair<Packet, Message>? = null
        runCatching {
            pair = SyncPacketPoster.INSTANCE.waitPacket(TerminalResponsePacket::class.java)
        }
        if (pair == null) {
            return -1
        }

        connected = true

        val message = pair!!.right as TerminalResponsePacket
        val response = message.response
        val result = StringReader(response).read(buf, offset, length)

        callback?.let {
            val len = buf.copyOfRange(offset, result)
            callback.response(len)
        }

        return StringReader(response).read(buf, offset, length)
    }

    override fun ready(): Boolean {
        return true
    }

}
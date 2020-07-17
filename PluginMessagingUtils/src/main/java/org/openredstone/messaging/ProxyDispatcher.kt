package org.openredstone.messaging

import com.google.common.io.ByteStreams
import net.md_5.bungee.api.connection.ProxiedPlayer

open class ProxyDispatcher {
    companion object {
        fun sendData(
            channel: String,
            subChannel: String,
            proxiedPlayer: ProxiedPlayer,
            data: String
        ) {
            val out = ByteStreams.newDataOutput()
            out.writeUTF(subChannel)
            out.writeUTF(data)
            proxiedPlayer.server.sendData(channel, out.toByteArray())
        }
    }
}

package org.openredstone.messaging

import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.openredstone.messages.ActionMessage
import java.util.*

object MessageProxyDispatcher : ProxyDispatcher() {
    fun sendMessage(
        proxyServer: ProxyServer,
        channel: String,
        subChannel: String,
        proxiedPlayer: ProxiedPlayer,
        message: ActionMessage
    ): Boolean {
        if (!targetPlayerExists(proxyServer, message.uuid)) {
            return false
        }
        sendData(channel, subChannel, proxiedPlayer, message.getSerializedMessage())
        return true
    }

    private fun targetPlayerExists(proxyServer: ProxyServer, uuid: UUID?): Boolean {
        return proxyServer.players.stream()
            .anyMatch { player: ProxiedPlayer -> player.uniqueId == uuid }
    }
}

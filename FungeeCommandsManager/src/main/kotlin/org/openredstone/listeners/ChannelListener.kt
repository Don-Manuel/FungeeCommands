package org.openredstone.listeners

import com.google.common.io.ByteStreams
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import org.openredstone.messages.ReportMessage

class ChannelListener(val channel: String, val pluginSubChannel: String) : Listener {
    @EventHandler
    fun onPluginMessage(e: PluginMessageEvent) {
        if (e.tag != channel) {
            return
        }
        val byteStream = ByteStreams.newDataInput(e.data)
        val subChannel = byteStream.readUTF()
        if (!pluginSubChannel.equals(subChannel, ignoreCase = true)) {
            return
        }
        val data = byteStream.readUTF()
        val reportMessage = ReportMessage(data)
        (e.receiver as ProxiedPlayer).sendMessage(
            *ComponentBuilder(
                reportMessage.arguments.joinToString(" ").substring(1)
            ).color(reportMessage.color).create()
        )
    }
}

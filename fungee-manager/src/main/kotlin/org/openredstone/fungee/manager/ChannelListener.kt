package org.openredstone.fungee.manager

import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.event.PluginMessageEvent
import net.md_5.bungee.api.plugin.Listener
import net.md_5.bungee.event.EventHandler
import org.openredstone.fungee.messages.FUNGEE_REPORT_CHANNEL
import org.openredstone.fungee.messages.Report
import org.openredstone.fungee.messages.ReportType

class ChannelListener : Listener {
    @EventHandler
    fun onPluginMessage(e: PluginMessageEvent) {
        if (e.tag != FUNGEE_REPORT_CHANNEL) {
            return
        }
        // idk
        val player = e.receiver as? ProxiedPlayer ?: return
        val report = Cbor.decodeFromByteArray<Report>(e.data)
        val message = ComponentBuilder(report.message)
            .color(report.chatColor)
            .create()
        player.sendMessage(*message)
    }

    private val Report.chatColor: ChatColor
        get() = when(this.type) {
            ReportType.INFO -> ChatColor.WHITE
            ReportType.WARNING -> ChatColor.YELLOW
            ReportType.ERROR -> ChatColor.RED
        }
}

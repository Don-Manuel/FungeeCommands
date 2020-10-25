package org.openredstone.fungee.executor

import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.openredstone.fungee.messages.*

class FungeeCommandsExecutor : JavaPlugin() {
    override fun onEnable() {
        if (!checkIfBungee()) return
        server.messenger.registerIncomingPluginChannel(
            this,
            FUNGEE_DISPATCH_CHANNEL,
            this::onPluginMessageReceived
        )
        logger.info("ProxyDispatcher enabled successfully.")
    }

    private fun checkIfBungee(): Boolean {
        if (server.spigot().config.getBoolean("settings.bungeecord")) {
            return true
        }
        """
            This server is not BungeeCord.
            If the server is already hooked to BungeeCord, please enable it into your spigot.yml as well.
            Plugin disabled!
        """.trimIndent()
            .lines()
            .forEach(logger::severe)
        server.pluginManager.disablePlugin(this)
        return false
    }

    private fun onPluginMessageReceived(
        channel: String,
        sender: Player,
        bytes: ByteArray
    ) {
        if (channel != FUNGEE_DISPATCH_CHANNEL) {
            // TODO: is this check needed?
            return
        }

        try {
            val action = Cbor.decodeFromByteArray<Action>(bytes)
            action.execute(this)
        } catch (e: Exception) {
            val reportMessage = Report(
                ReportType.ERROR,
                e.message ?: "unknown"
            )
            sender.sendPluginMessage(this, FUNGEE_REPORT_CHANNEL, Cbor.encodeToByteArray(reportMessage))
        }
    }
}

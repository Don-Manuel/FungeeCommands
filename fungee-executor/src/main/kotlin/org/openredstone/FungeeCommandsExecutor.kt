package org.openredstone

import org.bukkit.plugin.java.JavaPlugin
import org.openredstone.executors.ExecutionHandler

class FungeeCommandsExecutor : JavaPlugin() {
    private val channel = "fun:commands"

    override fun onEnable() {
        // idk
        if (!checkIfBungee()) return
        val executionHandler = ExecutionHandler(this)
        // ???
//        server.messenger.registerOutgoingPluginChannel(this, channel)
        server.messenger.registerIncomingPluginChannel(
            this,
            channel,
            PluginMessageEvent(channel, this, executionHandler)
        )
        logger.info("ProxyDispatcher enabled successfully.")
    }

    private fun checkIfBungee(): Boolean {
        if (!server.spigot().config.getBoolean("settings.bungeecord")) {
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
        return true
    }
}

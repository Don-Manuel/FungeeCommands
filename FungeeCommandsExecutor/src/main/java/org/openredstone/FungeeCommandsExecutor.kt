package org.openredstone

import org.bukkit.plugin.java.JavaPlugin
import org.openredstone.events.PluginMessageEvent
import org.openredstone.executors.ExecutionHandler

class FungeeCommandsExecutor : JavaPlugin() {
    var channel = "fun:commands"
    var subChannel = "dispatcher"

    override fun onEnable() {
        checkIfBungee()
        val executionHandler = ExecutionHandler(this)
        if (!server.pluginManager.isPluginEnabled(this)) {
            return
        }
        server.messenger.registerOutgoingPluginChannel(this, channel)
        server.messenger.registerIncomingPluginChannel(
            this,
            channel,
            PluginMessageEvent(channel, subChannel, this, executionHandler)
        )
        logger.info("ProxyDispatcher enabled successfully.")
    }

    private fun checkIfBungee() {
        if (!server.spigot().config.getBoolean("settings.bungeecord")) {
            logger.severe("This server is not BungeeCord.")
            logger.severe("If the server is already hooked to BungeeCord, please enable it into your spigot.yml aswell.")
            logger.severe("Plugin disabled!")
            server.pluginManager.disablePlugin(this)
        }
    }
}

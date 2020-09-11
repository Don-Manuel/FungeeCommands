package org.openredstone

import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Plugin
import org.openredstone.commands.*
import org.openredstone.handlers.DerpHandler
import org.openredstone.handlers.DynamicCommandHandler
import org.openredstone.handlers.LoadException
import org.openredstone.listeners.ChannelListener
import java.io.File
import java.util.*

class FungeeCommandsManager : Plugin() {
    val channel = "fun:commands"
    val subChannel = "dispatcher"

    override fun onEnable() {

        // slight mess here now
        val pluginFolder = dataFolder
        val pluginDescription = description
        val derpHandler = DerpHandler(File(pluginFolder, "derps.txt"))
        val commandFile = File(pluginFolder, "commands.json")
        val dynamicCommandHandler = DynamicCommandHandler(commandFile, this)
        registerCommands(
            Derp(this, proxy, derpHandler),
            Derps(this, derpHandler),
            FoodFight(this),
            Reload(this, derpHandler, dynamicCommandHandler),
            Rename(this),
            Slap(this),
            Milk(this),
            Version(this, pluginDescription.version)
        )
        proxy.pluginManager.registerListener(this, ChannelListener(channel, subChannel))
        proxy.registerChannel(channel)
        try {
            derpHandler.loadDerps()
            dynamicCommandHandler.loadCommands()
        } catch (e: LoadException) {
            // TODO maybe something better
            // there is still a bit of duplication here and in Reload
            e.printStackTrace()
        }
    }

    private fun registerCommands(vararg commands: Command) {
        Arrays.stream(commands)
            .forEach { command: Command? ->
                proxy.pluginManager.registerCommand(this, command)
            }
    }

    fun permissionFor(name: String): String {
        return "funcommands.$name"
    }

    fun getPlayer(name: String?): ProxiedPlayer? {
        return proxy.getPlayer(name)
    }
}

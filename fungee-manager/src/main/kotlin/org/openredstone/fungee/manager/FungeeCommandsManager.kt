package org.openredstone.fungee.manager

import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import net.md_5.bungee.api.plugin.Plugin
import org.openredstone.fungee.manager.handlers.DerpHandler
import org.openredstone.fungee.manager.handlers.DynamicCommandHandler
import org.openredstone.fungee.manager.handlers.LoadException
import org.openredstone.fungee.manager.commands.*
import org.openredstone.fungee.messages.FUNGEE_DISPATCH_CHANNEL
import org.openredstone.fungee.messages.FUNGEE_REPORT_CHANNEL
import java.io.File

class FungeeCommandsManager : Plugin() {
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
        // manager -> executor
        proxy.registerChannel(FUNGEE_DISPATCH_CHANNEL)
        // executor -> manager
        proxy.registerChannel(FUNGEE_REPORT_CHANNEL)
        proxy.pluginManager.registerListener(this, ChannelListener())
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
        commands.forEach { proxy.pluginManager.registerCommand(this, it) }
    }

    fun permissionFor(name: String): String = "funcommands.$name"

    fun getPlayer(name: String?): ProxiedPlayer? {
        return proxy.getPlayer(name)
    }
}

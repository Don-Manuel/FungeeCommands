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
        arrayOf(
            Derp(proxy, derpHandler),
            Derps(derpHandler),
            FoodFight(this),
            Reload(derpHandler, dynamicCommandHandler),
            Rename(),
            Slap(this),
            Milk(),
            Version(this, pluginDescription.version)
        ).forEach { proxy.pluginManager.registerCommand(this, it) }
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

    fun getPlayer(name: String?): ProxiedPlayer? = proxy.getPlayer(name)
}

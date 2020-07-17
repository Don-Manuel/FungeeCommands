package org.openredstone.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import org.openredstone.FungeeCommandsManager
import org.openredstone.handlers.DerpHandler
import org.openredstone.handlers.DynamicCommandHandler
import org.openredstone.handlers.LoadException

class Reload(plugin: FungeeCommandsManager, private val derpHandler: DerpHandler, private val dch: DynamicCommandHandler) : FunCommand(plugin, "funreload") {
    override fun execute(
        commandSender: CommandSender,
        strings: Array<String>
    ) {
        try {
            dch.loadCommands()
            derpHandler.loadDerps()
        } catch (e: LoadException) {
            e.printStackTrace()
            // ? long lines but tis not the best either
            commandSender.sendMessage(
                *ComponentBuilder("Could not reload FungeeCommandsManager: $e")
                    .color(ChatColor.RED).create()
            )
            return
        }
        commandSender.sendMessage(
            *ComponentBuilder("Reloaded FungeeCommandsManager.")
                .color(ChatColor.YELLOW).create()
        )
    }
}

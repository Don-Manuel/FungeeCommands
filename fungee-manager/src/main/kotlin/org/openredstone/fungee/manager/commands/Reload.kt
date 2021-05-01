package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.plugin.Command
import org.openredstone.fungee.manager.handlers.DerpHandler
import org.openredstone.fungee.manager.handlers.DynamicCommandHandler
import org.openredstone.fungee.manager.handlers.LoadException

class Reload(private val derpHandler: DerpHandler, private val dch: DynamicCommandHandler) : Command(
    "funreload", "funcommands.funreload"
) {
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

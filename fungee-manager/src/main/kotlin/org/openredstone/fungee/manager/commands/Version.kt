package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.plugin.Command
import org.openredstone.fungee.manager.FungeeCommandsManager

class Version(plugin: FungeeCommandsManager, private val version: String) : Command("funversion", "funcommands.funversion") {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        sender.sendMessage(
            *ComponentBuilder("FungeeCommandsManager version: ")
                .color(ChatColor.YELLOW)
                .append(version).color(ChatColor.RED).create()
        )
    }

}

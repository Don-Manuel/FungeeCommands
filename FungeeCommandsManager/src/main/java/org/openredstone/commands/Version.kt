package org.openredstone.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import org.openredstone.FungeeCommandsManager

class Version(plugin: FungeeCommandsManager, private val version: String) : FunCommand(plugin, "funversion") {
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

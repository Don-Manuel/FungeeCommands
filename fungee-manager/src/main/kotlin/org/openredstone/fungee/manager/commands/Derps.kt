package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import org.openredstone.fungee.manager.handlers.DerpHandler

class Derps(private val handler: DerpHandler) : Command("derps", "funcommands.derps") {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        handler.derps
            .mapIndexed { index, derp -> TextComponent("$index. $derp") }
            .forEach(sender::sendMessage)
    }
}

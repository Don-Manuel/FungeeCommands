package org.openredstone.commands

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import org.openredstone.FungeeCommandsManager
import org.openredstone.handlers.DerpHandler

class Derps(plugin: FungeeCommandsManager, private val handler: DerpHandler) : FunCommand(plugin, "derps") {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        (0 until handler.derpCount())
            .map(this::createDerpLine)
            .forEach(sender::sendMessage)
    }

    private fun createDerpLine(derpIndex: Int): TextComponent =
        TextComponent(
            derpIndex.toString() + ". " + handler.derpByIndex(derpIndex)
        )

}

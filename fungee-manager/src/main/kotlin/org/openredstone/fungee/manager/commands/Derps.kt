package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.TextComponent
import org.openredstone.fungee.manager.FungeeCommandsManager
import org.openredstone.fungee.manager.handlers.DerpHandler

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

package org.openredstone.commands

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.openredstone.FungeeCommandsManager
import org.openredstone.messages.ActionMessage
import org.openredstone.messaging.MessageProxyDispatcher
import org.openredstone.types.Action

class Rename(private val plugin: FungeeCommandsManager) : FunCommand(plugin, "rename") {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        val actionMessage =
            ActionMessage(Action.RENAME_ITEM, (sender as ProxiedPlayer).uniqueId, args)
        MessageProxyDispatcher.sendMessage(
            plugin.proxy,
            plugin.channel,
            plugin.subChannel,
            sender,
            actionMessage
        )
    }
}

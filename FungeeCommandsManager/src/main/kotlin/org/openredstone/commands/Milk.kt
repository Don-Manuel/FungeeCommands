package org.openredstone.commands

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.openredstone.FungeeCommandsManager
import org.openredstone.messages.ActionMessage
import org.openredstone.messaging.MessageProxyDispatcher
import org.openredstone.types.Action

class Milk(private val plugin: FungeeCommandsManager) : FunCommand(plugin, "milk") {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        val actionMessage =
            ActionMessage(Action.MILK, (sender as ProxiedPlayer).uniqueId, emptyArray())
        MessageProxyDispatcher.sendMessage(
            plugin.proxy,
            plugin.channel,
            plugin.subChannel,
            sender,
            actionMessage
        )
    }
}

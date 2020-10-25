package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.openredstone.fungee.manager.FungeeCommandsManager
import org.openredstone.fungee.manager.dispatch
import org.openredstone.fungee.messages.Action

class Milk(private val plugin: FungeeCommandsManager) : FunCommand(plugin, "milk") {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        val player = sender as? ProxiedPlayer ?: throw Exception("must be run by a player")
        player.dispatch(Action.Milk(player.uniqueId))
    }
}

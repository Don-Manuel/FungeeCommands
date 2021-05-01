package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import org.openredstone.fungee.manager.dispatch
import org.openredstone.fungee.messages.Action

class Rename : Command("rename", "funcommands.rename") {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        val newName = args.firstOrNull() ?: throw Exception("must provide a name")
        val player = sender as? ProxiedPlayer ?: throw Exception("must be run by a player")
        player.dispatch(Action.RenameItem(player.uniqueId, newName))
    }
}

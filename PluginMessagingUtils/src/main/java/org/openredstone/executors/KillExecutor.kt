package org.openredstone.executors

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.openredstone.messages.ActionMessage

class KillExecutor(plugin: Plugin) : Executor(plugin) {
    override fun execute(actionMessage: ActionMessage) {
        val player: Player =
            plugin.server.getPlayer(actionMessage.uuid)
                ?: throw Exception("Player not found.")
        player.health = 0.0
    }
}

package org.openredstone.executors

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.openredstone.messages.ActionMessage

class RenameItemExecutor(plugin: Plugin) : Executor(plugin) {
    override fun execute(actionMessage: ActionMessage) {
        val player: Player =
            plugin.server.getPlayer(actionMessage.uuid)
                ?: throw Exception("Player not found.")
        if (actionMessage.arguments.isEmpty()) {
            throw Exception("Not enough arguments")
        }
        val mainHand = player.inventory.itemInMainHand
        if (mainHand.type == Material.AIR) {
            throw Exception("Names cannot be applied to item type in main hand.")
        }
        val meta = mainHand.itemMeta
        meta!!.setDisplayName(actionMessage.arguments[0])
        mainHand.itemMeta = meta
    }
}

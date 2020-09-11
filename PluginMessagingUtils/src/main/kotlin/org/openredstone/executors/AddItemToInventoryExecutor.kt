package org.openredstone.executors

import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.openredstone.messages.ActionMessage

class AddItemToInventoryExecutor(plugin: Plugin) :
    Executor(plugin) {
    override fun execute(actionMessage: ActionMessage) {
        val player: Player =
            plugin.server.getPlayer(actionMessage.uuid)
                ?: throw Exception("Player not found.")
        if (actionMessage.arguments.isEmpty()) {
            throw Exception("Not enough arguments")
        }
        require(isValidMaterial(actionMessage)) { "Invalid material type: " + actionMessage.arguments[0] }
        val item = ItemStack(Material.valueOf(actionMessage.arguments[0]))
        if (actionMessage.arguments.size > 1) {
            item.itemMeta!!.setDisplayName(actionMessage.arguments[1])
        }
        player.inventory.addItem(item)
    }

    private fun isValidMaterial(actionMessage: ActionMessage): Boolean {
        val materialString = actionMessage.arguments[0]
        return Material.getMaterial(materialString) != null
    }
}

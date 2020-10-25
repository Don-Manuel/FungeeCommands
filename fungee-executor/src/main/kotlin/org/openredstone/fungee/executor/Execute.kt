package org.openredstone.fungee.executor

import org.bukkit.Material
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffectType
import org.openredstone.fungee.messages.Action

// TODO: this is really messy.. Execute is the only one that doesn't require a target player
fun Action.execute(plugin: Plugin) {
    when (this) {
        is Action.Kill -> {
            val player = plugin.server.getPlayer(targetPlayer) ?: throw Exception("Player not found")
            player.health = 0.0
        }
        is Action.Execute ->
            plugin.server.dispatchCommand(plugin.server.consoleSender, command)
        is Action.AddPotionEffect -> {
            val player = plugin.server.getPlayer(targetPlayer) ?: throw Exception("Player not found")
            val effect = PotionEffectType.getByName(effectName) ?: throw Exception("Invalid effect")
            player.addPotionEffect(effect.createEffect(duration, 1))
        }
        is Action.AddItemToInventory -> {
            val player = plugin.server.getPlayer(targetPlayer) ?: throw Exception("Player not found")
            val material = Material.getMaterial(materialName) ?: throw Exception("Invalid material")
            val item = ItemStack(material)
            if (displayName != null) {
                // cannot remember when this would return null
                item.itemMeta?.setDisplayName(displayName)
            }
            player.inventory.addItem(item)
        }
        is Action.RenameItem -> {
            val player = plugin.server.getPlayer(targetPlayer) ?: throw Exception("Player not found")
            val mainHand = player.inventory.itemInMainHand
            if (mainHand.type == Material.AIR) {
                throw Exception("Names cannot be applied to item type in main hand.")
            }
            val meta = mainHand.itemMeta
            // same here
            meta?.setDisplayName(displayName)
            mainHand.itemMeta = meta
        }
        is Action.Milk -> {
            val player = plugin.server.getPlayer(targetPlayer) ?: throw Exception("Player not found")
            player.activePotionEffects.forEach { effect ->
                player.removePotionEffect(effect.type)
            }
        }
    }
}

package org.openredstone.executors

import org.bukkit.entity.Player
import org.bukkit.plugin.Plugin
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType
import org.openredstone.messages.ActionMessage

class AddPotionEffectExecutor(plugin: Plugin) :
    Executor(plugin) {
    override fun execute(actionMessage: ActionMessage) {
        val player: Player =
            plugin.server.getPlayer(actionMessage.uuid)
                ?: throw Exception("Player not found")
        if (actionMessage.arguments.size < 2) {
            throw Exception("Not enough arguments")
        }
        require(hasValidPotion(actionMessage)) { "Invalid potion type: " + actionMessage.arguments[0] }
        require(isValidAmplifier(actionMessage)) { "Invalid amplifier: " + actionMessage.arguments[1] }
        player.addPotionEffect(
            PotionEffect(
                PotionEffectType.getByName(actionMessage.arguments[0])!!,
                Integer.valueOf(actionMessage.arguments[1]),
                1
            )
        )
    }

    private fun isValidAmplifier(actionMessage: ActionMessage): Boolean {
        val amplifierString = actionMessage.arguments[1]
        return amplifierString.matches("""\d+""".toRegex())
    }

    private fun hasValidPotion(actionMessage: ActionMessage): Boolean {
        val potionString = actionMessage.arguments[0]
        return PotionEffectType.getByName(potionString) != null
    }
}

package org.openredstone.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.openredstone.FungeeCommandsManager
import org.openredstone.messages.ActionMessage
import org.openredstone.messaging.MessageProxyDispatcher
import org.openredstone.types.Action
import java.util.*

class FoodFight(val plugin: FungeeCommandsManager) : FunCommand(plugin, "foodfight") {
    private val rand = Random()
    private val foods = listOf(
        "APPLE",
        "BAKED_POTATO",
        "BEEF",
        "BEETROOT",
        "BEETROOT_SOUP",
        "BREAD",
        "CARROT",
        "CHICKEN",
        "CHORUS_FRUIT",
        "COD",
        "COOKED_BEEF",
        "COOKED_CHICKEN",
        "COOKED_COD",
        "COOKED_MUTTON",
        "COOKED_PORKCHOP",
        "COOKED_RABBIT",
        "COOKED_SALMON",
        "COOKIE",
        "DRIED_KELP",
        "ENCHANTED_GOLDEN_APPLE",
        "GOLDEN_APPLE",
        "GOLDEN_CARROT",
        "MELON_SLICE",
        "MUSHROOM_STEW",
        "MUTTON",
        "POISONOUS_POTATO",
        "PORKCHOP",
        "POTATO",
        "PUMPKIN_PIE",
        "RABBIT",
        "RABBIT_STEW",
        "SALMON",
        "TROPICAL_FISH"
    )

    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        val victim: ProxiedPlayer = plugin.getPlayer(args[0]) ?: run {
            sender.sendMessage(
                *ComponentBuilder("No such player.").color(ChatColor.RED)
                    .create()
            )
            return
        }
        val victimName = victim.displayName
        val food = if (args.size > 1 && isValidFood(args[1])) {
            args[1]
        } else {
            foods.random()
        }
        val senderName = (sender as ProxiedPlayer).displayName
        val prettyFood = prettifyFood(food)
        val foodComponent =
            ComponentBuilder(senderName).color(ChatColor.DARK_PURPLE)
                .append(" threw a" + if (prettyFood.matches("""^[AEIOU].*""".toRegex())) "n " else " ")
                .color(ChatColor.YELLOW)
                .append(prettyFood).color(ChatColor.GOLD)
                .append(" at ").color(ChatColor.RED)
                .append(victimName).color(ChatColor.DARK_PURPLE)
                .append(".").color(ChatColor.YELLOW)
        val itemValues = arrayOf(food)
        val itemActionMessage =
            ActionMessage(Action.ADD_ITEM_TO_INVENTORY, victim.uniqueId, itemValues)
        MessageProxyDispatcher.sendMessage(
            plugin.proxy,
            plugin.channel,
            plugin.subChannel,
            sender,
            itemActionMessage
        )
        if (rand.nextInt(5) == 0) {
            foodComponent.append(" Headshot!").color(ChatColor.DARK_RED)
            val blindnessValues = arrayOf("BLINDNESS", "40")
            val confusionValues = arrayOf("CONFUSION", "40")
            val blindnessActionMessage =
                ActionMessage(Action.ADD_POTION_EFFECT, victim.uniqueId, blindnessValues)
            val confusionActionMessage =
                ActionMessage(Action.ADD_POTION_EFFECT, victim.uniqueId, confusionValues)
            MessageProxyDispatcher.sendMessage(
                plugin.proxy,
                plugin.channel,
                plugin.subChannel,
                sender,
                blindnessActionMessage
            )
            MessageProxyDispatcher.sendMessage(
                plugin.proxy,
                plugin.channel,
                plugin.subChannel,
                sender,
                confusionActionMessage
            )
        }
        plugin.proxy.broadcast(*foodComponent.create())
    }

    private fun isValidFood(food: String): Boolean = foods.contains(food)

    private fun prettifyFood(food: String): String =
        food.split("_").joinToString(separator = " ") { capitalizeWord(it) }

    private fun capitalizeWord(word: String): String =
        Character.toUpperCase(word[0]).toString() + word.substring(1).toLowerCase()
}

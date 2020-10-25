package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.openredstone.fungee.manager.FungeeCommandsManager
import org.openredstone.fungee.manager.dispatch
import org.openredstone.fungee.messages.Action
import java.util.*

class FoodFight(private val plugin: FungeeCommandsManager) : FunCommand(plugin, "foodfight") {
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
        victim.dispatch(Action.AddItemToInventory(victim.uniqueId, food))
        if (rand.nextInt(5) == 0) {
            foodComponent.append(" Headshot!").color(ChatColor.DARK_RED)
            victim.dispatch(Action.AddPotionEffect(victim.uniqueId, "BLINDNESS", 40))
            victim.dispatch(Action.AddPotionEffect(victim.uniqueId, "CONFUSION", 40))
        }
        plugin.proxy.broadcast(*foodComponent.create())
    }

    private fun isValidFood(food: String): Boolean = foods.contains(food)

    private fun prettifyFood(food: String): String =
        food.split("_").joinToString(separator = " ", transform = this::capitalizeWord)

    private fun capitalizeWord(word: String): String =
        Character.toUpperCase(word[0]).toString() + word.substring(1).toLowerCase()
}

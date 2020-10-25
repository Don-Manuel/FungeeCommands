package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.openredstone.fungee.manager.FungeeCommandsManager

class Slap(private val plugin: FungeeCommandsManager) : FunCommand(plugin, "slap") {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        if (args.isEmpty()) {
            sender.sendMessage(
                *ComponentBuilder("You must specify who you are slapping.")
                    .color(ChatColor.RED).create()
            )
        }
        val victim: ProxiedPlayer = plugin.getPlayer(args[0]) ?: run {
            sender.sendMessage(
                *ComponentBuilder("No such player.").color(ChatColor.RED)
                    .create()
            )
            return
        }
        val slap = "large trout"
        var victimName = victim.name
        if (victim == sender) {
            victimName = "themselves"
        }
        plugin.proxy.broadcast(
            *ComponentBuilder(sender.name)
                .color(ChatColor.DARK_PURPLE)
                .append(" slapped ").color(ChatColor.RED)
                .append(victimName).color(ChatColor.DARK_PURPLE)
                .append(" about a bit with a" + if (slap.matches("""^[aeiou].*""".toRegex())) "n " else " ")
                .color(ChatColor.RED)
                .append(slap).color(ChatColor.GOLD)
                .create()
        )
    }
}

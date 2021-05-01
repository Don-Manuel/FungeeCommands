package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent
import net.md_5.bungee.api.plugin.Command
import org.openredstone.fungee.manager.handlers.DerpHandler

class Derp(private val proxy: ProxyServer, private val handler: DerpHandler) : Command("derp", "funcommands.derp") {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) = when {
        args.size > 1 -> {
            sender.sendUsage("too many arguments")
        }
        args.isEmpty() -> {
            sender.sendDerp(handler.derps.random())
        }
        else -> {
            sender.sendDerpByIndex(args[0])
        }
    }

    private fun CommandSender.sendDerp(derp: String) {
        proxy.broadcast(buildDerp(name, derp))
    }

    private fun CommandSender.sendDerpByIndex(index: String) {
        index
            .toIntOrNull()
            ?.let(handler.derps::getOrNull)
            ?.let { sendDerp(it) }
            ?: sendUsage("argument must be a 0-based number within range")
    }

    private fun CommandSender.sendUsage(message: String) {
        // kinda weird, and not very user-friendly error messages
        arrayOf(
            message,
            "/derp derpNumber",
            "random derp: /derp",
            "There are currently " + handler.derps.size + " derps"
        ).forEach { sendError(it) }
    }

    private fun CommandSender.sendError(message: String) = sendMessage(
        *ComponentBuilder(message).color(ChatColor.RED).create()
    )

    private fun buildDerp(playerName: String, derp: String) = TextComponent(
        *ComponentBuilder(" * ").color(ChatColor.GREEN)
            .append(playerName).color(ChatColor.WHITE)
            .append(" DERP! ").color(ChatColor.DARK_BLUE)
            .append(derp).color(ChatColor.LIGHT_PURPLE)
            .create()
    )

}

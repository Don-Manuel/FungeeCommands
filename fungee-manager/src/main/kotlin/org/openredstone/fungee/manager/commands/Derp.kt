package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.ProxyServer
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.chat.TextComponent
import org.openredstone.fungee.manager.FungeeCommandsManager
import org.openredstone.fungee.manager.handlers.DerpHandler

class Derp(plugin: FungeeCommandsManager, private val proxy: ProxyServer, private val handler: DerpHandler) : FunCommand(plugin, "derp") {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        if (args.size > 1) {
            sendUsage(sender, "too many arguments")
            return
        }
        if (args.isEmpty()) {
            sendDerp(sender, handler.randomDerp())
        } else {
            sendDerpByIndex(sender, args[0])
        }
    }

    private fun sendDerp(sender: CommandSender, derp: String) {
        proxy.broadcast(
            buildDerp(
                sender.name,
                derp
            )
        )
    }

    private fun sendDerpByIndex(sender: CommandSender, index: String) {
        // this is sorta hacky
        try {
            val derpIndex = Integer.valueOf(index)
            sendDerp(sender, handler.derpByIndex(derpIndex))
        } catch (e: NumberFormatException) {
            sendUsage(
                sender,
                "argument must be a 0-based number within range"
            )
        } catch (e: IndexOutOfBoundsException) {
            sendUsage(
                sender,
                "argument must be a 0-based number within range"
            )
        }
    }

    private fun sendUsage(sender: CommandSender, message: String) {
        // kinda weird, and not very user-friendly error messages
        sendErrors(
            sender,
            message,
            "/derp derpNumber",
            "random derp: /derp",
            "There are currently " + handler.derpCount() + " derps"
        )
    }

    private fun sendErrors(sender: CommandSender, vararg messages: String) {
        for (message in messages) {
            sender.sendMessage(
                *ComponentBuilder(message).color(ChatColor.RED).create()
            )
        }
    }

    private fun buildDerp(playerName: String, derp: String): TextComponent {
        return TextComponent(
            *ComponentBuilder(" * ").color(ChatColor.GREEN)
                .append(playerName).color(ChatColor.WHITE)
                .append(" DERP! ").color(ChatColor.DARK_BLUE)
                .append(derp).color(ChatColor.LIGHT_PURPLE)
                .create()
        )
    }

}

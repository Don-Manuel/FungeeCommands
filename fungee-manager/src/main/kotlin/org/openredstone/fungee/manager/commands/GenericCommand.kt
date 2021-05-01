package org.openredstone.fungee.manager.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import org.openredstone.fungee.manager.FungeeCommandsManager
import org.openredstone.fungee.manager.dispatch
import org.openredstone.fungee.messages.Action

class GenericCommand(
    private val plugin: FungeeCommandsManager,
    command: String?,
    permission: String?,
    private val description: String,
    private val globalMessage: String?,
    private val localMessage: String?,
    private val toRun: String?
) : Command(command, permission) {
    override fun execute(
        sender: CommandSender,
        args: Array<String>
    ) {
        try {
            if (globalMessage != null) {
                plugin.proxy.broadcast(*ComponentBuilder(formatMessage(globalMessage, args, sender)).create())
            }
            if (localMessage != null) {
                sender.sendMessage(*ComponentBuilder(formatMessage(localMessage, args, sender)).create())
            }
            if (toRun != null) {
                val player = sender as? ProxiedPlayer ?: return
                player.dispatch(Action.Execute(formatMessage(toRun, args, sender)))
            }
        } catch (e: IllegalArgumentException) {
            // TODO: handle args properly
            sender.sendMessage(
                *ComponentBuilder("Invalid usage, with best regards ")
                    .color(ChatColor.RED)
                    .append(description).color(ChatColor.YELLOW)
                    .create()
            )
        }
    }
}

private fun formatMessage(
    rawText: String,
    args: Array<String>,
    sender: CommandSender
): String {
    var text = rawText
    if (sender is ProxiedPlayer) {
        text = text
            .replace("<uuid>", sender.uniqueId.toString())
    }
    return text
        .replace("<name>", sender.name)
        .replace("<arg-all>", args.joinToString(separator = " "))
        .formatArgs(args)
        .let { ChatColor.translateAlternateColorCodes('&', it) }
}

private fun String.formatArgs(args: Array<String>): String {
    var text = this
    for (i in 0..9) {
        val placeholder = "$$i"
        if (placeholder !in text) {
            // no checking for too many arguments currently
            break
        }
        require(i < args.size) {
            // TODO
            "not enough arguments"
        }
        text = text.replace(placeholder, args[i])
    }
    return text
}

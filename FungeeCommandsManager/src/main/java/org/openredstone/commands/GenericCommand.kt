package org.openredstone.commands

import net.md_5.bungee.api.ChatColor
import net.md_5.bungee.api.CommandSender
import net.md_5.bungee.api.chat.ComponentBuilder
import net.md_5.bungee.api.connection.ProxiedPlayer
import net.md_5.bungee.api.plugin.Command
import org.openredstone.FungeeCommandsManager
import org.openredstone.messages.ActionMessage
import org.openredstone.messaging.MessageProxyDispatcher
import org.openredstone.types.Action

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
                plugin.proxy.broadcast(
                    *ComponentBuilder(
                        formatMessage(
                            globalMessage,
                            args,
                            sender
                        )
                    ).create()
                )
            }
            if (localMessage != null) {
                sender.sendMessage(
                    *ComponentBuilder(formatMessage(localMessage, args, sender)).create()
                )
            }
            if (toRun != null) {
                if (sender !is ProxiedPlayer) {
                    return
                }
                val actionMessage = ActionMessage(
                    Action.EXECUTE,
                    sender.uniqueId,
                    formatMessage(toRun, args, sender).split(" ").toTypedArray()
                )
                MessageProxyDispatcher.sendMessage(
                    plugin.proxy,
                    plugin.channel,
                    plugin.subChannel,
                    sender,
                    actionMessage
                )
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

    private fun formatMessage(
        rawText: String,
        args: Array<String>,
        sender: CommandSender
    ): String {
        var text = rawText
        if (sender is ProxiedPlayer) {
            text = text
                .replace("<uuid>", sender.uniqueId.toString())
        } else require(toRun == null)
        text = text
            .replace("<name>", sender.name)
            .replace("<arg-all>", java.lang.String.join(" ", *args))
        text = text.formatArgs(args)
        text = ChatColor.translateAlternateColorCodes('&', text)
        return text
    }

    companion object {
        private fun String.formatArgs(args: Array<String>): String {
            var text = this
            for (i in 0..9) {
                val placeholder = "$$i"
                if (!text.contains(placeholder)) {
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
    }

}

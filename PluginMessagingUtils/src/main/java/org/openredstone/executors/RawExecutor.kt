package org.openredstone.executors

import org.bukkit.plugin.Plugin
import org.openredstone.messages.ActionMessage

class RawExecutor(plugin: Plugin) : Executor(plugin) {
    override fun execute(actionMessage: ActionMessage) {
        plugin.server.dispatchCommand(
            plugin.server.consoleSender,
            actionMessage.arguments.joinToString(" ")
        )
    }
}

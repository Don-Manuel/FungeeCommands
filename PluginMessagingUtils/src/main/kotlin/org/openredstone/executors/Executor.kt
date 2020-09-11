package org.openredstone.executors

import org.bukkit.plugin.Plugin
import org.openredstone.messages.ActionMessage

abstract class Executor(val plugin: Plugin) {
    abstract fun execute(actionMessage: ActionMessage)
}

package org.openredstone.commands

import net.md_5.bungee.api.plugin.Command
import org.openredstone.FungeeCommandsManager

abstract class FunCommand(plugin: FungeeCommandsManager, name: String) :
    Command(name, plugin.permissionFor(name))

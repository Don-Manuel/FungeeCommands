package org.openredstone.fungee.manager.handlers

import net.md_5.bungee.api.plugin.PluginManager
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.openredstone.fungee.manager.FungeeCommandsManager
import org.openredstone.fungee.manager.commands.GenericCommand
import java.io.File

class DynamicCommandHandler(private val commandFile: File, private val plugin: FungeeCommandsManager) {
    private val pluginManager: PluginManager = plugin.proxy.pluginManager
    private val registeredCommands = mutableListOf<GenericCommand>()

    fun loadCommands() {
        unregisterCommands()
        try {
            registerCommands()
        } catch (e: Exception) {
            throw LoadException(
                "Failed to load all commands from $commandFile. (${registeredCommands.size} commands were loaded)",
                e
            )
        }
    }

    private fun registerCommands() {
        readJSONCommands(commandFile)
            .map { it as? JSONObject ?: throw IllegalArgumentException("commands should be JSON objects") }
            .map {
                val name = it["command"] as String
                GenericCommand(
                    plugin,
                    name,
                    "funcommands.jsoncommand.$name",
                    it["description"] as String,
                    it["globalChat"] as String?,
                    it["localChat"] as String?,
                    it["run"] as String?,
                )
            }
            .forEach(::registerCommand)
    }

    private fun unregisterCommands() {
        registeredCommands.forEach(pluginManager::unregisterCommand)
        registeredCommands.clear()
    }

    private fun registerCommand(command: GenericCommand) {
        // intention: all currently registered commands are in registeredCommands
        // should the loading process for a command fail,
        // there's a way to cleanly reload the already loaded commands
        pluginManager.registerCommand(plugin, command)
        registeredCommands.add(command)
    }

    private fun readJSONCommands(file: File): JSONArray =
        JSONParser().parse(file.readText()) as? JSONArray
            ?: throw LoadException("Command file should contain an array of commands")
}

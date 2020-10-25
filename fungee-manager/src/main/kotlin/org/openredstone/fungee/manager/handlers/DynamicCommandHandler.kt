package org.openredstone.fungee.manager.handlers

import net.md_5.bungee.api.plugin.PluginManager
import org.json.simple.JSONArray
import org.json.simple.JSONObject
import org.json.simple.parser.JSONParser
import org.json.simple.parser.ParseException
import org.openredstone.fungee.manager.FungeeCommandsManager
import org.openredstone.fungee.manager.commands.GenericCommand
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.util.*
import java.util.function.Consumer

class DynamicCommandHandler(private val commandFile: File, private val plugin: FungeeCommandsManager) {
    private val pluginManager: PluginManager = plugin.proxy.pluginManager
    private val registeredCommands = ArrayList<GenericCommand>()

    // the exception handling here is still a bit weird
    fun loadCommands() {
        unregisterCommands()
        try {
            registerCommands()
        } catch (e: LoadException) {
            val message = ("Failed to load all commands from " + commandFile
                + ". (" + registeredCommands.size + " commands were loaded)")
            throw LoadException(message, e)
        } catch (e: ClassCastException) {
            val message = ("Failed to load all commands from " + commandFile
                + ". (" + registeredCommands.size + " commands were loaded)")
            throw LoadException(message, e)
        }
    }

    private fun registerCommands() {
        for (commandObj in readJSONCommands(commandFile)) {
            require(commandObj is JSONObject) { "commands should be JSON objects" }
            val cmd = commandObj

            // TODO: these casts are unsafe
            // and the whole thing is ugly
            // primitive obsession?
            val name = cmd["command"] as String?
            registerCommand(
                GenericCommand(
                    plugin,
                    name,
                    plugin.permissionFor("jsoncommand.$name"),
                    (cmd["description"] as String?)!!,
                    cmd.getOrDefault("globalChat", null) as String?,
                    cmd.getOrDefault("localChat", null) as String?,
                    cmd.getOrDefault("run", null) as String?
                )
            )
        }
    }

    private fun unregisterCommands() {
        registeredCommands.forEach(Consumer { command: GenericCommand? ->
            pluginManager.unregisterCommand(
                command
            )
        })
        registeredCommands.clear()
    }

    private fun registerCommand(command: GenericCommand) {
        // intention: all currently registered commands are in registeredCommands
        // should the loading process for a command fail,
        // there's a way to cleanly reload the already loaded commands
        pluginManager.registerCommand(plugin, command)
        registeredCommands.add(command)
    }

    private fun readJSONCommands(file: File): JSONArray {
        try {
            FileReader(file).use { reader ->
                return JSONParser().parse(reader) as? JSONArray
                    ?: throw LoadException("Command file should contain an array of commands")
            }
        } catch (e: ParseException) {
            throw LoadException(e)
        } catch (e: IOException) {
            throw LoadException(e)
        }
    }
}

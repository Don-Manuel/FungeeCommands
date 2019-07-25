package org.openredstone.handlers;

import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openredstone.commands.GenericCommand;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class DynamicCommandHandler {
    private final File commandFile;
    private final Plugin plugin;
    private final PluginManager pluginManager;
    private ArrayList<GenericCommand> registeredCommands = new ArrayList<>();

    public DynamicCommandHandler(File commandFile, Plugin plugin) {
        this.commandFile = commandFile;
        // this is a very unfortunate kitchen sink dependency
        this.plugin = plugin;
        this.pluginManager = plugin.getProxy().getPluginManager();
    }

    // the exception handling here is still a bit weird
    public void loadCommands() throws LoadException {
        unregisterCommands();
        try {
            registerCommands();
        } catch (LoadException | ClassCastException e) {
            String message = "Failed to load all commands from " + commandFile
                    + ". (" + registeredCommands.size() + " commands were loaded)";
            throw new LoadException(message, e);
        }
    }

    private void registerCommands() throws LoadException {
        for (Object commandObj : readJSONCommands(commandFile)) {
            if (!(commandObj instanceof JSONObject)) {
                throw new IllegalArgumentException("commands should be JSON objects");
            }
            JSONObject cmd = (JSONObject) commandObj;

            // TODO: these casts are unsafe
            // and the whole thing is ugly
            // primitive obsession?
            String name = (String) cmd.get("command");
            registerCommand(new GenericCommand(
                    name,
                    (String) cmd.get("description"),
                    (String) cmd.getOrDefault("globalChat", null),
                    (String) cmd.getOrDefault("localChat", null),
                    (String) cmd.getOrDefault("run", null)
            ));
        }
    }

    private void unregisterCommands() {
        registeredCommands.forEach(pluginManager::unregisterCommand);
        registeredCommands.clear();
    }

    private void registerCommand(GenericCommand command) {
        // intention: all currently registered commands are in registeredCommands
        // should the loading process for a command fail,
        // there's a way to cleanly reload the already loaded commands
        pluginManager.registerCommand(plugin, command);
        registeredCommands.add(command);
    }

    private JSONArray readJSONCommands(File file) throws LoadException {
        try (FileReader reader = new FileReader(file)) {
            Object json = new JSONParser().parse(reader);
            if (!(json instanceof JSONArray)) {
                throw new LoadException("Command file should contain an array of commands");
            }
            return (JSONArray) json;
        } catch (ParseException | IOException e) {
            throw new LoadException(e);
        }
    }
}

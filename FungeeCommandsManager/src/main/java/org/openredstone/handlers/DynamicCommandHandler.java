package org.openredstone.handlers;

import net.md_5.bungee.api.plugin.Plugin;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.commands.GenericCommand;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;

public class DynamicCommandHandler {
    private static final File commandFile =  new File(FungeeCommandsManager.pluginFolder, "commands.json");
    private static ArrayList<GenericCommand> commands = null;

    public static void readCommands() throws Exception {
        commands = new ArrayList<>();

        // TODO: move somewhere?
        FungeeCommandsManager.logger.info("Reading FungeeCommandsManager from commands.json");

        for (Object commandObj : readJSONCommands(commandFile)) {
            if (!(commandObj instanceof JSONObject)) {
                throw new Exception("Commands should be JSON objects");
            }
            JSONObject cmd = (JSONObject) commandObj;

            // TODO: these casts are unsafe
            // and the whole thing is ugly
            String name = (String) cmd.get("command");
            commands.add(new GenericCommand(
                    name,
                    "FungeeCommandsManager." + name,
                    (String) cmd.get("description"),
                    (String) cmd.getOrDefault("globalChat", null),
                    (String) cmd.getOrDefault("localChat", null),
                    (String) cmd.getOrDefault("run", null)
            ));
        }
    }

    private static JSONArray readJSONCommands(File file) throws Exception {
        try (FileReader reader = new FileReader(file)) {
            Object json = new JSONParser().parse(reader);
            if (!(json instanceof JSONArray)) {
                // TODO
                throw new Exception("commands.json should contain an array of commands");
            }
            return (JSONArray) json;
        } catch (ParseException e) {
            // TODO
            throw e;
        }
    }

    public static void registerCommands(Plugin plugin) {
        for (GenericCommand command : commands) {
            plugin.getProxy().getPluginManager().registerCommand(plugin, command);
        }

    }

    public static void unregisterCommands(Plugin plugin) {
        for (GenericCommand command : commands) {
            plugin.getProxy().getPluginManager().unregisterCommand(command);
        }
    }
}

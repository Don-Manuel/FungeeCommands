package org.openredstone;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;
import org.openredstone.commands.*;
import org.openredstone.handlers.DerpHandler;
import org.openredstone.handlers.DynamicCommandHandler;
import org.openredstone.listeners.ChannelListener;

import java.io.File;
import java.util.Arrays;
import java.util.logging.Logger;

public class FungeeCommandsManager extends Plugin {

    private static final String rootPermission = "funcommands";
    public static final String channel = "fun:commands";
    public static final String subChannel = "dispatcher";
    public static ProxyServer proxy;
    public static Logger logger;
    public static File pluginFolder;
    public static PluginDescription pluginDescription;
    public static Plugin plugin;

    public static String permissionFor(String name) {
        return rootPermission + "." + name;
    }

    @Override
    public void onEnable() {

        proxy = getProxy();
        logger = getLogger();
        pluginFolder = getDataFolder();
        pluginDescription = getDescription();
        plugin = this;

        registerCommands(
                new Derp(proxy),
                new Derps(),
                new FoodFight(),
                new Reload(this),
                new Rename(),
                new Shrug(),
                new Slap(),
                new Version()
        );

        proxy.getPluginManager().registerListener(this, new ChannelListener());
        proxy.registerChannel(channel);

        // also TODO: get load/unload/reload functionality into one place
        DerpHandler.loadDerps();

        try {
            DynamicCommandHandler.readCommands();
            DynamicCommandHandler.registerCommands(this);
        } catch (Exception e) {
            // TODO
            e.printStackTrace();
        }
    }

    private void registerCommands(Command... commands) {
        for (Command command : commands) {
            proxy.getPluginManager().registerCommand(this, command);
        }
    }
    public static ProxiedPlayer getPlayer(String name) throws Exception {
        for (ProxiedPlayer proxiedPlayer : proxy.getPlayers()) {
            if (proxiedPlayer.getDisplayName().equals(name)) {
                return proxiedPlayer;
            }
        }
        throw new Exception("NoPlayerFound");
    }

}

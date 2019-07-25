package org.openredstone;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;
import org.openredstone.commands.*;
import org.openredstone.handlers.DerpHandler;
import org.openredstone.handlers.DynamicCommandHandler;
import org.openredstone.handlers.LoadException;
import org.openredstone.listeners.ChannelListener;

import java.io.File;
import java.util.logging.Logger;

public class FungeeCommandsManager extends Plugin {
    public static final String channel = "fun:commands";
    public static final String subChannel = "dispatcher";
    public static ProxyServer proxy;
    private Logger logger;
    private File pluginFolder;
    public static PluginDescription pluginDescription;
    public static Plugin plugin;

    public static String permissionFor(String name) {
        return "funcommands." + name;
    }

    @Override
    public void onEnable() {
        proxy = getProxy();
        logger = getLogger();
        pluginFolder = getDataFolder();
        pluginDescription = getDescription();
        plugin = this;

        DerpHandler derpHandler = new DerpHandler(new File(pluginFolder, "derps.txt"));

        File commandFile = new File(pluginFolder, "commands.json");
        DynamicCommandHandler dynamicCommandHandler = new DynamicCommandHandler(commandFile, this);

        registerCommands(
                new Derp(proxy, derpHandler),
                new Derps(derpHandler),
                new FoodFight(),
                new Reload(derpHandler, dynamicCommandHandler),
                new Rename(),
                new Shrug(),
                new Slap(),
                new Version()
        );

        proxy.getPluginManager().registerListener(this, new ChannelListener());
        proxy.registerChannel(channel);

        try {
            derpHandler.loadDerps();
            dynamicCommandHandler.loadCommands();
        } catch (LoadException e) {
            // TODO maybe something better
            // there is still a bit of duplication here and in Reload
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

package org.openredstone;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginDescription;
import org.openredstone.commands.*;
import org.openredstone.handlers.DerpHandler;
import org.openredstone.handlers.DynamicCommandHandler;

import java.io.File;
import java.util.logging.Logger;

public class FungeeCommandsManager extends Plugin {

    public static String channel = "fun:commands";
    public static String subChannel = "dispatcher";
    public static ProxyServer proxy;
    public static Logger logger;
    public static File pluginFolder;
    public static PluginDescription pluginDescription;
    public static Plugin plugin;

    @Override
    public void onEnable() {

        proxy = getProxy();
        logger = getLogger();
        pluginFolder = getDataFolder();
        pluginDescription = getDescription();
        plugin = this;

        getProxy().getPluginManager().registerCommand(this, new Derp());
        getProxy().getPluginManager().registerCommand(this, new Derps());
        getProxy().getPluginManager().registerCommand(this, new FoodFight());
        getProxy().getPluginManager().registerCommand(this, new Reload(this));
        getProxy().getPluginManager().registerCommand(this, new Rename());
        getProxy().getPluginManager().registerCommand(this, new Slap());
        getProxy().getPluginManager().registerCommand(this, new Shrug());
        getProxy().getPluginManager().registerCommand(this, new Version());

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

    public static ProxiedPlayer getPlayer(String name) throws Exception {
        for (ProxiedPlayer proxiedPlayer : proxy.getPlayers()) {
            if (proxiedPlayer.getDisplayName().equals(name)) {
                return proxiedPlayer;
            }
        }
        throw new Exception("NoPlayerFound");
    }

}

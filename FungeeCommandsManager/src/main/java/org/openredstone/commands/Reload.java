package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.handlers.DerpHandler;
import org.openredstone.handlers.DynamicCommandHandler;

public class Reload extends Command {
    Plugin plugin;

    public Reload(Plugin plugin) {
        super("funreload", FungeeCommandsManager.permissionFor("funreload"));
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        try {
            DynamicCommandHandler.unregisterCommands(plugin);
            DynamicCommandHandler.readCommands();
            DynamicCommandHandler.registerCommands(plugin);
            DerpHandler.loadDerps();
        } catch (Exception e) {
            // TODO: nail down the possible exceptions and report them in a better way
            e.printStackTrace();
            commandSender.sendMessage(new ComponentBuilder("Could not reload FungeeCommandsManager").color(ChatColor.RED).create());
            return;
        }
        commandSender.sendMessage(new ComponentBuilder("Reloaded FungeeCommandsManager.").color(ChatColor.YELLOW).create());
    }
}

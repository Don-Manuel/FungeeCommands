package org.openredstone.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import net.md_5.bungee.api.plugin.Plugin;
import org.openredstone.handlers.DerpHandler;
import org.openredstone.handlers.DynamicCommandHandler;

public class Reload extends Command {
    Plugin plugin;

    public Reload(Plugin plugin) {
        super("funreload");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if (!commandSender.hasPermission("funcommands" + this.getClass().getSimpleName())) {
            commandSender.sendMessage(new TextComponent("You do not have permission to run this command!"));
            return;
        }

        try {
            DynamicCommandHandler.unregisterCommands(plugin);
            DynamicCommandHandler.readCommands();
            DynamicCommandHandler.registerCommands(plugin);
            DerpHandler.loadDerps();
        } catch (Exception e) {
            // TODO: nail down the possible exceptions and report them in a better way
            e.printStackTrace();
            commandSender.sendMessage(new TextComponent("Could not reload FungeeCommandsManager"));
            return;
        }
        commandSender.sendMessage(new TextComponent("Reloaded FungeeCommandsManager."));
    }
}

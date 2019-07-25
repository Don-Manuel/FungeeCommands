package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;

public class Shrug extends Command {
    public Shrug() {
        super("shrug", FungeeCommandsManager.permissionFor("shrug"));
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        FungeeCommandsManager.proxy.broadcast(new ComponentBuilder(commandSender.getName() + " ").color(ChatColor.RED)
                .append(String.join(" ", strings)).color(ChatColor.YELLOW)
                .append(" ¯\\_(ツ)_/¯").color(ChatColor.GOLD)
                .create()
        );
    }
}

package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;

public class Shrug extends Command {
    public Shrug() {
        super("shrug");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!commandSender.hasPermission(FungeeCommandsManager.rootPermission + "." + this.getClass().getSimpleName())){
            commandSender.sendMessage(FungeeCommandsManager.noPermissions);
            return;
        }
        FungeeCommandsManager.proxy.broadcast(new ComponentBuilder(commandSender.getName() + " ").color(ChatColor.RED)
                .append(String.join(" ", strings)).color(ChatColor.YELLOW)
                .append(" ¯\\_(ツ)_/¯").color(ChatColor.GOLD)
                .create()
        );
    }
}

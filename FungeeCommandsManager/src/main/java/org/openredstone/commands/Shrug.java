package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;

public class Shrug extends Command {

    public Shrug() {
        super("shrug");
    }

    @Override
    public void execute(CommandSender commandSender, String[] args) {
        if(!commandSender.hasPermission("funcommands" + this.getClass().getSimpleName())){
            commandSender.sendMessage(new TextComponent("You do not have permission to run this command!"));
            return;
        }
        commandSender.sendMessage(new TextComponent(ChatColor.DARK_GRAY + commandSender.getName() + ChatColor.RESET + " " + String.join(" ", args) + " " + ChatColor.GOLD + "¯\\_(ツ)_/¯"));
    }
}

package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.openredstone.FungeeCommandsManager;

public class Shrug extends FunCommand {
    public Shrug() {
        super("shrug");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        FungeeCommandsManager.proxy.broadcast(new ComponentBuilder(sender.getName() + " ").color(ChatColor.RED)
                .append(String.join(" ", args)).color(ChatColor.YELLOW)
                .append(" ¯\\_(ツ)_/¯").color(ChatColor.GOLD)
                .create()
        );
    }
}

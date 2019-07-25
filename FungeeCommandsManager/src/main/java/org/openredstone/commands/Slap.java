package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.openredstone.FungeeCommandsManager;

public class Slap extends FunCommand {
    public Slap() {
        super("slap");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length == 0) {
            sender.sendMessage(new ComponentBuilder("You must specify who you are slapping.").color(ChatColor.RED).create());
        }

        ProxiedPlayer victim;

        try {
            victim = FungeeCommandsManager.getPlayer(args[0]);
        } catch(Exception e) {
            sender.sendMessage(new ComponentBuilder("No such player.").color(ChatColor.RED).create());
            return;
        }

        String slap = "large trout";
        String victimName = victim.getName();

        if (victim.equals(sender)) {
            victimName = "themselves";
        }

        FungeeCommandsManager.proxy.broadcast(new ComponentBuilder(sender.getName()).color(ChatColor.DARK_PURPLE)
                .append(" slapped ").color(ChatColor.RED)
                .append(victimName).color(ChatColor.DARK_PURPLE)
                .append(" about a bit with a" + (slap.matches("^[aeiou].*") ? "n " : " ")).color(ChatColor.RED)
                .append(slap).color(ChatColor.GOLD)
                .create()
        );
    }
}

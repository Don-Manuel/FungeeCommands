package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;

public class Slap extends Command {
    public Slap() {
        super("slap", FungeeCommandsManager.permissionFor("slap"));
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(strings.length <= 0) {
            commandSender.sendMessage(new ComponentBuilder("You must specify who you are slapping.").color(ChatColor.RED).create());
        }

        ProxiedPlayer victim;

        try{
            victim = FungeeCommandsManager.getPlayer(strings[0]);
        }catch(Exception e){
            commandSender.sendMessage(new ComponentBuilder("No such player.").color(ChatColor.RED).create());
            return;
        }

        String slap = "large trout";
        String victimName = victim.getName();

        if (victim.equals(commandSender)) {
            victimName = "themselves";
        }

        FungeeCommandsManager.proxy.broadcast(new ComponentBuilder(commandSender.getName()).color(ChatColor.DARK_PURPLE)
                .append(" slapped ").color(ChatColor.RED)
                .append(victimName).color(ChatColor.DARK_PURPLE)
                .append(" about a bit with a" + (slap.matches("^[aeiou].*") ? "n " : " ")).color(ChatColor.RED)
                .append(slap).color(ChatColor.GOLD)
                .create()
        );
    }
}

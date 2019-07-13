package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;

public class Slap extends Command {
    public Slap() {
        super("slap");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!commandSender.hasPermission("funcommands" + this.getClass().getSimpleName())){
            commandSender.sendMessage(new TextComponent("You do not have permission to run this command!"));
            return;
        }

        if(strings.length <= 0) {
            commandSender.sendMessage(new TextComponent(ChatColor.RED+"[ERROR] You must specify who you are slapping"));
        }

        ProxiedPlayer victim;

        try{
            victim = FungeeCommandsManager.getPlayer(strings[0]);
        }catch(Exception e){
            commandSender.sendMessage(new TextComponent(ChatColor.RED+"[ERROR] No such player."));
            return;
        }

        String slap = "large trout";
        String victimName = "";

        try{
            victimName = victim.getName();
        }catch(Exception e){
            commandSender.sendMessage(new TextComponent(ChatColor.RED+"[ERROR] No such player."));
            return;
        }

        if (victim.equals(commandSender)) {
            victimName = "themselves";
        }

        try {
            slap = strings[1];
        } catch (Exception e) {

        }

        FungeeCommandsManager.proxy.broadcast(new TextComponent(ChatColor.DARK_PURPLE + commandSender.getName() +
                ChatColor.RED + " slapped " +
                ChatColor.DARK_PURPLE + victimName +
                ChatColor.RED + " about a bit with a" +
                (slap.matches("^[aeiou].*") ? "n " : " ") + ChatColor.GOLD + slap));
    }
}

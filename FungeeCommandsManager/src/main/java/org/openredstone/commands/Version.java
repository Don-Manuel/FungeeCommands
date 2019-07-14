package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;

public class Version extends Command {
    public Version() {
        super("version");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!commandSender.hasPermission(FungeeCommandsManager.rootPermission + "." + this.getClass().getSimpleName())){
            commandSender.sendMessage(FungeeCommandsManager.noPermissions);
            return;
        }
        commandSender.sendMessage(new ComponentBuilder("FungeeCommandsManager version: ").color(ChatColor.YELLOW)
                .append(FungeeCommandsManager.pluginDescription.getVersion()).color(ChatColor.RED).create());
    }
}

package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.openredstone.FungeeCommandsManager;

public class Version extends FunCommand {
    public Version() {
        super("funversion");
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        commandSender.sendMessage(new ComponentBuilder("FungeeCommandsManager version: ").color(ChatColor.YELLOW)
                .append(FungeeCommandsManager.pluginDescription.getVersion()).color(ChatColor.RED).create());
    }
}

package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;

public class Version extends FunCommand {
    private final String version;

    public Version(String version) {
        super("funversion");
        this.version = version;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        sender.sendMessage(new ComponentBuilder("FungeeCommandsManager version: ").color(ChatColor.YELLOW)
                .append(version).color(ChatColor.RED).create());
    }
}

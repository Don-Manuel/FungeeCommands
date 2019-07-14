package org.openredstone.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
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
        commandSender.sendMessage(new TextComponent("FungeeCommandsManager version: " + FungeeCommandsManager.pluginDescription.getVersion()));
    }
}

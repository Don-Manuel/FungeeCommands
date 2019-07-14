package org.openredstone.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.handlers.DerpHandler;

public class Derps extends Command {
    public Derps() {
        super("derps");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!commandSender.hasPermission(FungeeCommandsManager.rootPermission + "." + this.getClass().getSimpleName())){
            commandSender.sendMessage(FungeeCommandsManager.noPermissions);
            return;
        }

        for (TextComponent derp : DerpHandler.getDerpList(commandSender)) {
            commandSender.sendMessage(derp);
        }
    }
}

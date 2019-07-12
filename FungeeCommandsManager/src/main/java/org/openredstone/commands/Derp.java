package org.openredstone.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.handlers.DerpHandler;

public class Derp extends Command {
    public Derp() {
        super("derp");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!commandSender.hasPermission("funcommands" + this.getClass().getSimpleName())){
            commandSender.sendMessage(new TextComponent("You do not have permission to run this command!"));
            return;
        }
        DerpHandler.sendDerp(commandSender, strings);
    }
}

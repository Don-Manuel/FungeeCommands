package org.openredstone.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.handlers.MessageDispatchHandler;
import org.openredstone.messaging.Action;
import org.openredstone.messaging.Message;

public class Rename extends Command {
    public Rename() {
        super("rename");
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        if(!commandSender.hasPermission("funcommands" + this.getClass().getSimpleName())){
            commandSender.sendMessage(new TextComponent("You do not have permission to run this command!"));
            return;
        }
        Message message = new Message(Action.RENAME_ITEM, ((ProxiedPlayer) commandSender).getUniqueId(), strings[0]);
        MessageDispatchHandler.sendData((ProxiedPlayer) commandSender, message.getSerializedMessage());
    }
}

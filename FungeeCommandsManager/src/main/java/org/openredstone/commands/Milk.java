package org.openredstone.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.messages.ActionMessage;
import org.openredstone.messaging.MessageProxyDispatcher;
import org.openredstone.types.Action;

public class Milk extends FunCommand {

    public Milk() {
        super("milk");
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        ActionMessage actionMessage = new ActionMessage(Action.MILK, ((ProxiedPlayer) sender).getUniqueId(), null);
        MessageProxyDispatcher.sendMessage(
                FungeeCommandsManager.proxy,
                FungeeCommandsManager.channel,
                FungeeCommandsManager.subChannel,
                (ProxiedPlayer) sender,
                actionMessage);
    }
}
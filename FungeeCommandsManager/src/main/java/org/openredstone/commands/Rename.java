package org.openredstone.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.messages.ActionMessage;
import org.openredstone.messaging.MessageProxyDispatcher;
import org.openredstone.types.Action;

public class Rename extends Command {
    public Rename() {
        super("rename", FungeeCommandsManager.permissionFor("rename"));
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        ActionMessage actionMessage = new ActionMessage(Action.RENAME_ITEM, ((ProxiedPlayer) commandSender).getUniqueId(), strings);
        MessageProxyDispatcher.sendMessage(
                FungeeCommandsManager.proxy,
                FungeeCommandsManager.channel,
                FungeeCommandsManager.subChannel,
                (ProxiedPlayer) commandSender,
                actionMessage);

    }
}

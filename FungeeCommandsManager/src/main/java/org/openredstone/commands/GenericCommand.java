package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.messages.ActionMessage;
import org.openredstone.messaging.MessageProxyDispatcher;
import org.openredstone.types.Action;

public class GenericCommand extends Command {
    private final String description;
    private final String globalMessage;
    private final String localMessage;
    private final String toRun;

    public GenericCommand(
            String command,
            String description,
            String globalMessage,
            String localMessage,
            String toRun
    ) {
        super(command, FungeeCommandsManager.permissionFor(command));
        this.description = description;
        this.globalMessage = globalMessage;
        this.localMessage = localMessage;
        this.toRun = toRun;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        try {
            if (globalMessage != null) {
                FungeeCommandsManager.proxy.broadcast(new ComponentBuilder(formatMessage(globalMessage, args, sender)).create());
            }

            if (localMessage != null) {
                sender.sendMessage(new ComponentBuilder(formatMessage(localMessage, args, sender)).create());
            }

            if (toRun != null) {
                if (!(sender instanceof ProxiedPlayer)) {
                    return;
                }

                ActionMessage actionMessage = new ActionMessage(
                        Action.EXECUTE,
                        ((ProxiedPlayer) sender).getUniqueId(),
                        formatMessage(toRun, args, sender).split(" "));

                MessageProxyDispatcher.sendMessage(
                        FungeeCommandsManager.proxy,
                        FungeeCommandsManager.channel,
                        FungeeCommandsManager.subChannel,
                        (ProxiedPlayer) sender,
                        actionMessage);
            }
        } catch (IllegalArgumentException e) {
            // TODO: handle args properly

            sender.sendMessage(new ComponentBuilder("Invalid usage, with best regards ").color(ChatColor.RED)
                    .append(description).color(ChatColor.YELLOW)
                    .create()
            );
        }
    }

    private String formatMessage(String text, String[] args, CommandSender sender) throws IllegalArgumentException {
        if (sender instanceof  ProxiedPlayer) {
            text = text
                    .replace("<uuid>", ((ProxiedPlayer) sender).getUniqueId().toString());
        } else if (toRun != null) {
            throw new IllegalArgumentException();
        }

        text = text
                .replace("<name>", sender.getName())
                .replace("<arg-all>", String.join(" ", args));
        text = formatArgs(text, args);
        text = ChatColor.translateAlternateColorCodes('&', text);
        return text;
    }

    private static String formatArgs(String text, String[] args) throws IllegalArgumentException {
        for (int i = 0; i <  10; ++i) {
            String placeholder = "$" + i;
            if (!text.contains(placeholder)) {
                // no checking for too many arguments currently
                break;
            }
            if (i >= args.length) {
                // TODO
                throw new IllegalArgumentException("not enough arguments");
            }
            text = text.replace(placeholder, args[i]);
        }
        return text;
    }
}

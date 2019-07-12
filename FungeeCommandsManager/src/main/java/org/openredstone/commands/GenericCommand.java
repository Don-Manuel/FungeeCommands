package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;

public class GenericCommand extends Command {
    private final String description;
    private final String globalMessage;
    private final String localMessage;
    private final String toRun;

    public GenericCommand(
            String command,
            String permission,
            String description,
            String globalMessage,
            String localMessage,
            String toRun
    ) {
        super(command, permission);
        this.description = description;
        this.globalMessage = globalMessage;
        this.localMessage = localMessage;
        this.toRun = toRun;
    }

    // TODO #2: api update to 1.13, delete this
    private boolean hasPermission(CommandSender sender) {
        return getPermission() == null
                || getPermission().equals("")
                || sender.hasPermission(getPermission());
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!hasPermission(sender)) {
            sender.sendMessage(new TextComponent(ChatColor.RED + "You do not have permission to run this command!"));
            return;
        }

        try {
            if (globalMessage != null) {
                FungeeCommandsManager.proxy.broadcast(formatMessage(globalMessage, args, sender));
            }

            if (localMessage != null) {
                sender.sendMessage(formatMessage(localMessage, args, sender));
            }

            if (toRun != null) {
                // TODO
                sender.sendMessage(formatMessage(toRun, args, sender));
            }
        } catch (IllegalArgumentException e) {
            // TODO: handle args properly
            sender.sendMessage(new TextComponent(
                    ChatColor.RED + "Invalid usage, with best regards " + description
            ));
        }
    }

    private TextComponent formatMessage(String text, String[] args, CommandSender sender) throws IllegalArgumentException {

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
        return new TextComponent(text);
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

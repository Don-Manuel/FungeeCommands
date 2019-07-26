package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.TextComponent;
import org.openredstone.handlers.DerpHandler;

public class Derp extends FunCommand {
    private final ProxyServer proxy;
    private final DerpHandler handler;

    public Derp(ProxyServer proxy, DerpHandler handler) {
        super("derp");
        this.proxy = proxy;
        this.handler = handler;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (args.length > 1) {
            sendUsage(sender, "too many arguments");
            return;
        }

        if (args.length == 0) {
            sendDerp(sender, handler.randomDerp());
        } else {
            sendDerpByIndex(sender, args[0]);
        }
    }

    private void sendDerp(CommandSender sender, String derp) {
        proxy.broadcast(buildDerp(
                sender.getName(),
                derp
        ));
    }

    private void sendDerpByIndex(CommandSender sender, String index) {
        // this is sorta hacky
        try {
            int derpIndex = Integer.valueOf(index);
            sendDerp(sender, handler.derpByIndex(derpIndex));
        } catch (NumberFormatException | IndexOutOfBoundsException e) {
            sendUsage(
                    sender,
                    "argument must be a 0-based number within range"
            );
        }
    }

    private void sendUsage(CommandSender sender, String message) {
        // kinda weird, and not very user-friendly error messages
        sendErrors(
                sender,
                message,
                "/derp derpNumber",
                "random derp: /derp",
                "There are currently " + handler.derpCount() + " derps"
        );
    }

    private void sendErrors(CommandSender sender, String... messages) {
        for (String message : messages) {
            sender.sendMessage(new ComponentBuilder(message).color(ChatColor.RED).create());
        }
    }

    private TextComponent buildDerp(String playerName, String derp) {
        return new TextComponent(
                new ComponentBuilder(" * ").color(ChatColor.GREEN)
                        .append(playerName).color(ChatColor.WHITE)
                        .append(" DERP! ").color(ChatColor.DARK_BLUE)
                        .append(derp).color(ChatColor.LIGHT_PURPLE)
                        .create()
        );
    }
}

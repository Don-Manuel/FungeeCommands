package org.openredstone.commands;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.chat.ComponentBuilder;
import org.openredstone.handlers.DerpHandler;
import org.openredstone.handlers.DynamicCommandHandler;
import org.openredstone.handlers.LoadException;

public class Reload extends FunCommand {
    private final DerpHandler derpHandler;
    private final DynamicCommandHandler dch;

    public Reload(DerpHandler derpHandler, DynamicCommandHandler dch) {
        super("funreload");
        this.derpHandler = derpHandler;
        this.dch = dch;
    }

    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        try {
            dch.loadCommands();
            derpHandler.loadDerps();
        } catch (LoadException e) {
            e.printStackTrace();
            // ? long lines but tis not the best either
            commandSender.sendMessage(
                    new ComponentBuilder("Could not reload FungeeCommandsManager: " + e)
                            .color(ChatColor.RED).create()
            );
            return;
        }
        commandSender.sendMessage(
                new ComponentBuilder("Reloaded FungeeCommandsManager.").color(ChatColor.YELLOW).create()
        );
    }
}

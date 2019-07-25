package org.openredstone.commands;

import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.handlers.DerpHandler;

public class Derp extends Command {
    public Derp() {
        super("derp", FungeeCommandsManager.permissionFor("derp"));
    }
    @Override
    public void execute(CommandSender commandSender, String[] strings) {
        FungeeCommandsManager.proxy.broadcast(DerpHandler.getDerp(commandSender, strings));
    }
}

package org.openredstone.commands;

import net.md_5.bungee.api.plugin.Command;
import org.openredstone.FungeeCommandsManager;

public abstract class FunCommand extends Command {
    public FunCommand(String name) {
        super(name, FungeeCommandsManager.permissionFor(name));
    }
}

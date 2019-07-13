package org.openredstone.executors;

import org.bukkit.plugin.Plugin;
import org.openredstone.messages.ActionMessage;

public class RawExecutor extends Executor {

    public RawExecutor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(ActionMessage actionMessage) {
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), String.join(" ", actionMessage.getArguments()));
    }
}

package org.openredstone.executors;

import org.bukkit.plugin.Plugin;
import org.openredstone.messaging.Message;

public class RawExecutor extends Executor {

    public RawExecutor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(Message message) {
        plugin.getServer().dispatchCommand(plugin.getServer().getConsoleSender(), String.join(" ", message.getArguments()));
    }
}

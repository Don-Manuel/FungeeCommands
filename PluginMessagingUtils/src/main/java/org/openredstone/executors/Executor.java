package org.openredstone.executors;

import org.bukkit.plugin.Plugin;
import org.openredstone.messaging.Message;

public abstract class Executor {

    static Plugin plugin;

    public Executor(Plugin plugin) {
        this.plugin = plugin;
    }

    public abstract void execute(Message message) throws Exception;

}

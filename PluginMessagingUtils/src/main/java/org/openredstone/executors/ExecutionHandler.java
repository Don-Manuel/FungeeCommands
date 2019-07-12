package org.openredstone.executors;

import org.bukkit.plugin.Plugin;
import org.openredstone.messaging.Message;

public class ExecutionHandler {

    Plugin plugin;
    KillExecutor killExecutor;
    RawExecutor rawExecutor;
    AddItemToInventoryExecutor addItemToInventoryExecutor;

    public ExecutionHandler(Plugin plugin) {
        this.plugin = plugin;
        initializeExecutors(plugin);
    }

    public void execute(Message message) throws Exception {
        switch (message.getAction()) {
            case KILL:
                killExecutor.execute(message);
                return;
            case EXECUTE:
                rawExecutor.execute(message);
                return;
            case ADD_ITEM_TO_INVENTORY:
                addItemToInventoryExecutor.execute(message);
                return;
        }
    }

    private void initializeExecutors(Plugin plugin) {
        this.killExecutor = new KillExecutor(plugin);
        this.rawExecutor = new RawExecutor(plugin);
        this.addItemToInventoryExecutor = new AddItemToInventoryExecutor(plugin);
    }
}

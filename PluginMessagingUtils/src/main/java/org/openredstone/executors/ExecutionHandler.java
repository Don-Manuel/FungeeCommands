package org.openredstone.executors;

import org.bukkit.plugin.Plugin;
import org.openredstone.messages.ActionMessage;

public class ExecutionHandler {

    Plugin plugin;
    KillExecutor killExecutor;
    RawExecutor rawExecutor;
    RenameItemExecutor renameItemExecutor;
    AddPotionEffectExecutor addPotionEffectExecutor;
    AddItemToInventoryExecutor addItemToInventoryExecutor;

    public ExecutionHandler(Plugin plugin) {
        this.plugin = plugin;
        initializeExecutors(plugin);
    }

    public void execute(ActionMessage actionMessage) throws Exception {
        switch (actionMessage.getAction()) {
            case KILL:
                killExecutor.execute(actionMessage);
                return;
            case EXECUTE:
                rawExecutor.execute(actionMessage);
                return;
            case RENAME_ITEM:
                renameItemExecutor.execute(actionMessage);
                return;
            case ADD_POTION_EFFECT:
                addPotionEffectExecutor.execute(actionMessage);
                return;
            case ADD_ITEM_TO_INVENTORY:
                addItemToInventoryExecutor.execute(actionMessage);
                return;
        }
    }

    private void initializeExecutors(Plugin plugin) {
        this.killExecutor = new KillExecutor(plugin);
        this.rawExecutor = new RawExecutor(plugin);
        this.renameItemExecutor = new RenameItemExecutor(plugin);
        this.addPotionEffectExecutor = new AddPotionEffectExecutor(plugin);
        this.addItemToInventoryExecutor = new AddItemToInventoryExecutor(plugin);
    }
}

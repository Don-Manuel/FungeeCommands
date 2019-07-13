package org.openredstone.executors;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.openredstone.messaging.Message;

public class AddItemToInventoryExecutor extends Executor {

    public AddItemToInventoryExecutor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(Message message) throws Exception {
        Player player = plugin.getServer().getPlayer(message.getUuid());

        if (player == null) {
            throw new Exception("Player not found.");
        }

        if (message.getArguments().length <= 1) {
            throw new Exception("Not enough arguments");
        }

        if(!isValidMaterial(message)) {
            throw new IllegalArgumentException("Invalid material type: " + message.getArguments()[0]);
        }

        player.getInventory().addItem(new ItemStack(Material.valueOf(message.getArguments()[0])));
    }

    private boolean isValidMaterial(Message message) {
        String materialString = message.getArguments()[0];
        return Material.getMaterial(materialString) != null;
    }
}

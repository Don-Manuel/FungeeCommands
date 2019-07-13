package org.openredstone.executors;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.openredstone.messages.ActionMessage;

public class AddItemToInventoryExecutor extends Executor {

    public AddItemToInventoryExecutor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(ActionMessage actionMessage) throws Exception {
        Player player = plugin.getServer().getPlayer(actionMessage.getUuid());

        if (player == null) {
            throw new Exception("Player not found.");
        }

        if (actionMessage.getArguments().length < 1) {
            throw new Exception("Not enough arguments");
        }

        if (!isValidMaterial(actionMessage)) {
            throw new IllegalArgumentException("Invalid material type: " + actionMessage.getArguments()[0]);
        }

        ItemStack item = new ItemStack(Material.valueOf(actionMessage.getArguments()[0]));

        if (actionMessage.getArguments().length > 1) {
            item.getItemMeta().setDisplayName(actionMessage.getArguments()[1]);
        }

        player.getInventory().addItem(item);
    }

    private boolean isValidMaterial(ActionMessage actionMessage) {
        String materialString = actionMessage.getArguments()[0];
        return Material.getMaterial(materialString) != null;
    }
}

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

        player.getInventory().addItem(new ItemStack(Material.valueOf(message.getArguments()[0])));
    }
}

package org.openredstone.executors;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.openredstone.messaging.Message;

public class RenameItemExecutor extends Executor {

    public RenameItemExecutor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(Message message) throws Exception {
        Player player = plugin.getServer().getPlayer(message.getUuid());

        if (player == null) {
            throw new Exception("Player not found.");
        }

        if (message.getArguments().length < 1) {
            throw new Exception("Not enough arguments");
        }

        ItemStack mainHand = player.getInventory().getItemInMainHand();

        if (mainHand.getType().equals(Material.AIR)) {
            throw new Exception("Names cannot be applied to item type in main hand.");
        }

        ItemMeta meta = mainHand.getItemMeta();
        meta.setDisplayName(message.getArguments()[0]);
        mainHand.setItemMeta(meta);

    }
}

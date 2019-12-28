package org.openredstone.executors;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.openredstone.messages.ActionMessage;

import java.util.Collection;

public class MilkExecutor extends Executor {

    public MilkExecutor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(ActionMessage actionMessage) throws Exception {
        Player player = plugin.getServer().getPlayer(actionMessage.getUuid());

        if (player == null) {
            throw new Exception("Player not found.");
        }

        Collection<PotionEffect> potions = player.getActivePotionEffects();
        for (PotionEffect potion : potions) {
            player.removePotionEffect(potion.getType());
        }
    }
}

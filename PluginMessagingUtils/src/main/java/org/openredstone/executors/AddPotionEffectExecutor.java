package org.openredstone.executors;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.openredstone.messages.ActionMessage;

public class AddPotionEffectExecutor extends Executor {

    public AddPotionEffectExecutor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(ActionMessage actionMessage) throws Exception, IllegalArgumentException {
        Player player = plugin.getServer().getPlayer(actionMessage.getUuid());

        if (player == null) {
            throw new Exception("Player not found");
        }

        if (actionMessage.getArguments().length < 2) {
            throw new Exception("Not enough arguments");
        }

        if (!hasValidPotion(actionMessage)) {
            throw new IllegalArgumentException("Invalid potion type: " + actionMessage.getArguments()[0]);
        }

        if (!isValidAmplifier(actionMessage)) {
            throw new IllegalArgumentException("Invalid amplifier: " + actionMessage.getArguments()[1]);
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(actionMessage.getArguments()[0]), Integer.valueOf(actionMessage.getArguments()[1]), 1));

    }

    private boolean isValidAmplifier(ActionMessage actionMessage) {
        String amplifierString = actionMessage.getArguments()[1];
        return amplifierString.matches("\\d+");
    }

    private boolean hasValidPotion(ActionMessage actionMessage) {
        String potionString = actionMessage.getArguments()[0];
        return PotionEffectType.getByName(potionString) != null;
    }
}

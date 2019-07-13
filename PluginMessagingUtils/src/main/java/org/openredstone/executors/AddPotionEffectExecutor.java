package org.openredstone.executors;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.openredstone.messaging.Message;

public class AddPotionEffectExecutor extends Executor {

    public AddPotionEffectExecutor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(Message message) throws Exception, IllegalArgumentException {
        Player player = plugin.getServer().getPlayer(message.getUuid());

        if (player == null) {
            throw new Exception("Player not found");
        }

        if (message.getArguments().length < 2) {
            throw new Exception("Not enough arguments");
        }

        if (!hasValidPotion(message)) {
            throw new IllegalArgumentException("Invalid potion type: " + message.getArguments()[0]);
        }

        if (!isValidAmplifier(message)) {
            throw new IllegalArgumentException("Invalid amplifier: " + message.getArguments()[1]);
        }

        player.addPotionEffect(new PotionEffect(PotionEffectType.getByName(message.getArguments()[0]), Integer.valueOf(message.getArguments()[1]), 1));

    }

    private boolean isValidAmplifier(Message message) {
        String amplifierString = message.getArguments()[1];
        return amplifierString.matches("\\d+");
    }

    private boolean hasValidPotion(Message message) {
        String potionString = message.getArguments()[0];
        return PotionEffectType.getByName(potionString) != null;
    }
}

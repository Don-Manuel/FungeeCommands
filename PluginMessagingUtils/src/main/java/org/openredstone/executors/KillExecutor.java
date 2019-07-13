package org.openredstone.executors;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.openredstone.messages.ActionMessage;

public class KillExecutor extends Executor {

    public KillExecutor(Plugin plugin) {
        super(plugin);
    }

    @Override
    public void execute(ActionMessage actionMessage) throws Exception {
        Player player = plugin.getServer().getPlayer(actionMessage.getUuid());

        if (player == null) {
            throw new Exception("Player not found.");
        }

        player.setHealth(0);
    }
}

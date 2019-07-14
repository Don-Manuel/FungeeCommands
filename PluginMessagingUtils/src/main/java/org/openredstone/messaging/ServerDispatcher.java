package org.openredstone.messaging;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class ServerDispatcher {
    public static void sendData (JavaPlugin plugin, Player player, String mainChannel, String subChannel, String data) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(subChannel);
        out.writeUTF(data);
        player.sendPluginMessage(plugin, mainChannel, out.toByteArray());
    }
}

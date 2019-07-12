package org.openredstone.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.openredstone.FungeeCommandsExecutor;
import org.openredstone.executors.ExecutionHandler;
import org.openredstone.messaging.Message;
import org.openredstone.messaging.MessageDispatcher;

public class PluginMessageEvent implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!FungeeCommandsExecutor.channel.equalsIgnoreCase(channel)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput( bytes );
        String subChannel = in.readUTF();

        if (!subChannel.equalsIgnoreCase(subChannel)) {
            return;
        }

        String data = in.readUTF();

        try {
            Message message = new Message(data);
            FungeeCommandsExecutor.executionHandler.execute(message);
        } catch (Exception e) {
            FungeeCommandsExecutor.plugin.getLogger().warning(e.toString());
        }

    }
}

package org.openredstone.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.openredstone.FungeeCommandsExecutor;
import org.openredstone.messages.ActionMessage;

public class PluginMessageEvent implements PluginMessageListener {
    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!FungeeCommandsExecutor.channel.equalsIgnoreCase(channel)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput( bytes );
        String mainchannel = in.readUTF();
        String subChannel = in.readUTF();

        if (!mainchannel.equalsIgnoreCase(channel)) {
            return;
        }
        if (!subChannel.equalsIgnoreCase(subChannel)) {
            return;
        }

        String data = in.readUTF();

        try {
            ActionMessage actionMessage = new ActionMessage(data);
            FungeeCommandsExecutor.executionHandler.execute(actionMessage);
        } catch (Exception e) {
            e.printStackTrace();
            FungeeCommandsExecutor.plugin.getLogger().warning(e.toString());
        }

    }

}

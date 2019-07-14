package org.openredstone.events;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.openredstone.FungeeCommandsExecutor;
import org.openredstone.messages.ActionMessage;
import org.openredstone.messages.ReportMessage;
import org.openredstone.messaging.ServerDispatcher;
import org.openredstone.types.Report;

public class PluginMessageEvent implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] bytes) {
        if (!FungeeCommandsExecutor.channel.equalsIgnoreCase(channel)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput( bytes );
        String subChannel = in.readUTF();

        if (!subChannel.equalsIgnoreCase(FungeeCommandsExecutor.subChannel)) {
            return;
        }

        String data = in.readUTF();

        try {
            ActionMessage actionMessage = new ActionMessage(data);
            FungeeCommandsExecutor.executionHandler.execute(actionMessage);
        } catch (Exception e) {
            ReportMessage reportMessage = new ReportMessage(
              Report.ERROR,
              e.toString().split(" ")
            );
            ServerDispatcher.sendData(
                    FungeeCommandsExecutor.plugin,
                    player,
                    FungeeCommandsExecutor.channel,
                    FungeeCommandsExecutor.subChannel,
                    reportMessage.getSerializedMessage()
            );
        }

    }

}

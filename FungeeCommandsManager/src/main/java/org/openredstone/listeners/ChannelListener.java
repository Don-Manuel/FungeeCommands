package org.openredstone.listeners;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;
import org.openredstone.FungeeCommandsManager;
import org.openredstone.messages.ReportMessage;

public class ChannelListener implements Listener {
    @EventHandler
    public void onPluginMessage(PluginMessageEvent e) throws Exception {
        if (!e.getTag().equals(FungeeCommandsManager.channel)) {
            return;
        }

        ByteArrayDataInput in = ByteStreams.newDataInput(e.getData());
        String subChannel = in.readUTF();

        if (!subChannel.equalsIgnoreCase(FungeeCommandsManager.subChannel)) {
            return;
        }

        String data = in.readUTF();
        ReportMessage reportMessage = new ReportMessage(data);
        ((ProxiedPlayer) e.getReceiver()).sendMessage(new TextComponent(reportMessage.getColor() + String.join(" ", reportMessage.getArguments()).substring(1)));
    }
}

package org.openredstone.messaging;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import net.md_5.bungee.api.connection.ProxiedPlayer;

public class Dispatcher {
    public static void sendData(String channel, String subChannel, ProxiedPlayer proxiedPlayer, String data) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF(channel);
        out.writeUTF(subChannel);
        out.writeUTF(data);
        proxiedPlayer.getServer().sendData(channel, out.toByteArray());
    }
}

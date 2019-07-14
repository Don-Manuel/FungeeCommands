package org.openredstone.messaging;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import org.openredstone.messages.Message;

import java.util.UUID;

public class MessageDispatcher extends Dispatcher {
    public static boolean sendMessage(ProxyServer proxyServer, String channel, String subChannel, ProxiedPlayer proxiedPlayer, Message message) {
        if (!targetPlayerExists(proxyServer, message.getUuid())) {
            return false;
        }
        sendData(channel, subChannel, proxiedPlayer, message.getSerializedMessage());
        return true;
    }
    private static boolean targetPlayerExists(ProxyServer proxyServer, UUID uuid) {
        for (ProxiedPlayer proxiedPlayer : proxyServer.getPlayers()) {
            if (proxiedPlayer.getUniqueId().equals(uuid)) {
                return true;
            }
        }
        return false;
    }
}

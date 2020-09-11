package org.openredstone.messaging

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin

object ServerDispatcher {
    fun sendData(
        plugin: JavaPlugin,
        player: Player,
        mainChannel: String,
        subChannel: String,
        data: String
    ) {
        val out = ByteStreams.newDataOutput()
        out.writeUTF(subChannel)
        out.writeUTF(data)
        player.sendPluginMessage(plugin, mainChannel, out.toByteArray())
    }
}

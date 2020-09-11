package org.openredstone.events

import com.google.common.io.ByteStreams
import org.bukkit.entity.Player
import org.bukkit.plugin.java.JavaPlugin
import org.bukkit.plugin.messaging.PluginMessageListener
import org.openredstone.executors.ExecutionHandler
import org.openredstone.messages.ActionMessage
import org.openredstone.messages.ReportMessage
import org.openredstone.messaging.ServerDispatcher
import org.openredstone.types.Report

class PluginMessageEvent(private val pluginChannel: String, private val pluginSubChannel: String, private val plugin: JavaPlugin, private val executionHandler: ExecutionHandler) : PluginMessageListener {
    override fun onPluginMessageReceived(
        channel: String,
        player: Player,
        bytes: ByteArray
    ) {
        if (!pluginChannel.equals(channel, ignoreCase = true)) {
            return
        }
        val byteStream = ByteStreams.newDataInput(bytes)
        val subChannel = byteStream.readUTF()
        if (!pluginSubChannel.equals(subChannel, ignoreCase = true)) {
            return
        }
        val data = byteStream.readUTF()
        try {
            val actionMessage = ActionMessage(data)
            executionHandler.execute(actionMessage)
        } catch (e: Exception) {
            val reportMessage = ReportMessage(
                Report.ERROR,
                e.toString().split(" ").toTypedArray()
            )
            ServerDispatcher.sendData(
                plugin,
                player,
                channel,
                subChannel,
                reportMessage.getSerializedMessage()
            )
        }
    }
}

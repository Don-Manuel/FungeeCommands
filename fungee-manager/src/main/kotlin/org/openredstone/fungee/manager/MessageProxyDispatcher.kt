package org.openredstone.fungee.manager

import kotlinx.serialization.cbor.Cbor
import kotlinx.serialization.encodeToByteArray
import net.md_5.bungee.api.connection.ProxiedPlayer
import org.openredstone.fungee.messages.Action
import org.openredstone.fungee.messages.FUNGEE_DISPATCH_CHANNEL

fun ProxiedPlayer.dispatch(
    message: Action,
) {
    server.sendData(FUNGEE_DISPATCH_CHANNEL, Cbor.encodeToByteArray(message))
}

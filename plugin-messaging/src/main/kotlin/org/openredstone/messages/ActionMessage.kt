package org.openredstone.messages

import org.openredstone.types.Action
import java.util.*

class ActionMessage {
    var arguments: Array<String>
    var action: Action
    var uuid: UUID

    constructor(action: Action, uuid: UUID, arguments: Array<String>) {
        this.arguments = arguments
        this.action = action
        this.uuid = uuid
    }

    constructor(serializedMessage: String) {
        val raw: Array<String> = serializedMessage.split(":").toTypedArray()
        if (raw.size < 2) {
            throw Exception("Not enough arguments provided in serialized message.")
        }
        action = Action.valueOf(raw[0])
        uuid = UUID.fromString(raw[1])
        require(isValidAction(raw[0])) { "Invalid action: " + raw[0] }
        require(isValidUuid) { "Invalid uuid: " + raw[1] }
        arguments = raw.copyOfRange(2, raw.size)
    }

    fun getSerializedMessage(): String =
        action.name + ":" + uuid.toString() + ":" + arguments.joinToString(":")


    private val isValidUuid: Boolean
        get() = uuid.toString().matches("[0-9a-f]{8}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{4}-[0-9a-f]{12}".toRegex())

    companion object {
        private fun isValidAction(action: String?): Boolean {
            return try {
                Action.valueOf(action!!)
                true
            } catch (e: IllegalArgumentException) {
                false
            }
        }
    }
}

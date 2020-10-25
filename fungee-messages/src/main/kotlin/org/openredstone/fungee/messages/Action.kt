@file:UseSerializers(UUIDSerializer::class)

package org.openredstone.fungee.messages

import kotlinx.serialization.KSerializer
import kotlinx.serialization.Serializable
import kotlinx.serialization.UseSerializers
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import java.util.*

const val FUNGEE_DISPATCH_CHANNEL = "fungee:dispatch"
const val FUNGEE_REPORT_CHANNEL = "fungee:report"

@Serializable
sealed class Action {
    @Serializable
    data class Kill(val targetPlayer: UUID) : Action()

    @Serializable
    data class Execute(val command: String) : Action()

    // TODO: amplifier? this was kinda confused in the previous implementation
    @Serializable
    data class AddPotionEffect(
        val targetPlayer: UUID,
        // :( stringly typed
        val effectName: String,
        val duration: Int,
    ) : Action()

    @Serializable
    data class AddItemToInventory(
        val targetPlayer: UUID,
        // :( stringly typed
        val materialName: String,
        val displayName: String? = null,
    ) : Action()

    @Serializable
    data class RenameItem(val targetPlayer: UUID, val displayName: String) : Action()

    @Serializable
    data class Milk(val targetPlayer: UUID) : Action()
}

// serialization stuff

@Serializable
private data class UUIDSurrogate(val high: Long, val low: Long, ) {
    fun toUUID(): UUID = UUID(high, low)
    companion object {
        fun fromUUID(uuid: UUID) = UUIDSurrogate(uuid.mostSignificantBits, uuid.leastSignificantBits)
    }
}

private object UUIDSerializer : KSerializer<UUID> {
    override val descriptor: SerialDescriptor =
        UUIDSurrogate.serializer().descriptor

    override fun deserialize(decoder: Decoder): UUID =
        decoder.decodeSerializableValue(UUIDSurrogate.serializer()).toUUID()

    override fun serialize(encoder: Encoder, value: UUID) {
        encoder.encodeSerializableValue(UUIDSurrogate.serializer(), UUIDSurrogate.fromUUID(value))
    }

}

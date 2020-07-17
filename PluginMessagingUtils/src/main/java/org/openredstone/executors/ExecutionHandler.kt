package org.openredstone.executors

import org.bukkit.GameMode
import org.bukkit.plugin.Plugin
import org.openredstone.messages.ActionMessage
import org.openredstone.types.Action

class ExecutionHandler(var plugin: Plugin) {
    private val killExecutor = KillExecutor(plugin)
    private val milkExecutor = MilkExecutor(plugin)
    private val rawExecutor = RawExecutor(plugin)
    private val renameItemExecutor = RenameItemExecutor(plugin)
    private val addPotionEffectExecutor = AddPotionEffectExecutor(plugin)
    private val addItemToInventoryExecutor = AddItemToInventoryExecutor(plugin)

    fun execute(actionMessage: ActionMessage) {
        val player = plugin.server.getPlayer(actionMessage.uuid) ?: return
        if (player.gameMode == GameMode.SURVIVAL) {
            return
        }
        when (actionMessage.action) {
            Action.KILL -> killExecutor.execute(actionMessage)
            Action.EXECUTE -> rawExecutor.execute(actionMessage)
            Action.RENAME_ITEM -> renameItemExecutor.execute(actionMessage)
            Action.ADD_POTION_EFFECT -> addPotionEffectExecutor.execute(actionMessage)
            Action.ADD_ITEM_TO_INVENTORY -> addItemToInventoryExecutor.execute(actionMessage)
            Action.MILK -> milkExecutor.execute(actionMessage)
        }
    }
}

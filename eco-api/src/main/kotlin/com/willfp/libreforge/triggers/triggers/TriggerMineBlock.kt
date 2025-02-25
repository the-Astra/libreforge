package com.willfp.libreforge.triggers.triggers

import com.willfp.eco.core.integrations.antigrief.AntigriefManager
import com.willfp.eco.core.integrations.mcmmo.McmmoManager
import com.willfp.libreforge.triggers.Trigger
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter
import com.willfp.libreforge.triggers.wrappers.WrappedBlockBreakEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.block.BlockBreakEvent

class TriggerMineBlock : Trigger(
    "mine_block", listOf(
        TriggerParameter.PLAYER,
        TriggerParameter.BLOCK,
        TriggerParameter.LOCATION,
        TriggerParameter.EVENT
    )
) {
    @EventHandler(ignoreCancelled = true)
    fun handle(event: BlockBreakEvent) {
        if (McmmoManager.isFake(event)) {
            return
        }

        val player = event.player
        val block = event.block
        if (!AntigriefManager.canBreakBlock(player, block)) {
            return
        }

        this.processTrigger(
            player,
            TriggerData(
                player = player,
                block = block,
                location = block.location,
                event = WrappedBlockBreakEvent(event)
            )
        )
    }
}

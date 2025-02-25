package com.willfp.libreforge.effects.effects

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.ConfigViolation
import com.willfp.libreforge.effects.Effect
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter
import com.willfp.libreforge.triggers.Triggers

class EffectExplosion : Effect(
    "explosion",
    applicableTriggers = Triggers.withParameters(
        TriggerParameter.LOCATION
    )
) {
    override fun handle(data: TriggerData, config: Config) {
        val location = data.location ?: return
        val world = location.world ?: return

        val amount = config.getIntFromExpression("amount", data.player)
        val power = config.getDoubleFromExpression("power", data.player)

        for (i in 1..amount) {
            plugin.scheduler.runLater({
                world.createExplosion(location, power)
            }, 1)
        }
    }

    override fun validateConfig(config: Config): List<ConfigViolation> {
        val violations = mutableListOf<ConfigViolation>()

        if (!config.has("amount")) violations.add(
                ConfigViolation(
                    "amount",
                    "You must specify the amount of explosions!"
                )
            )

        if (!config.has("power")) violations.add(
                ConfigViolation(
                    "power",
                    "You must specify the power of the explosion!"
                )
            )

        return violations
    }
}
package com.willfp.libreforge.effects.effects

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.ConfigViolation
import com.willfp.libreforge.effects.Effect
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter
import com.willfp.libreforge.triggers.Triggers
import org.bukkit.util.Vector

class EffectSetVelocity : Effect(
    "multiply_velocity",
    applicableTriggers = Triggers.withParameters(
        TriggerParameter.PLAYER
    )
) {
    override fun handle(data: TriggerData, config: Config) {
        val player = data.player ?: return

        player.velocity = Vector(
            config.getDoubleFromExpression("x", player),
            config.getDoubleFromExpression("y", player),
            config.getDoubleFromExpression("z", player)
        )
    }

    override fun validateConfig(config: Config): List<ConfigViolation> {
        val violations = mutableListOf<ConfigViolation>()

        if (!config.has("x")) violations.add(
            ConfigViolation(
                "x",
                "You must specify the velocity x component!"
            )
        )

        if (!config.has("y")) violations.add(
            ConfigViolation(
                "y",
                "You must specify the velocity y component!"
            )
        )

        if (!config.has("z")) violations.add(
            ConfigViolation(
                "z",
                "You must specify the velocity z component!"
            )
        )

        return violations
    }
}

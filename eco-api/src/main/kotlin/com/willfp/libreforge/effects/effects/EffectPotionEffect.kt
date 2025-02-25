package com.willfp.libreforge.effects.effects

import com.willfp.eco.core.config.interfaces.Config
import com.willfp.libreforge.ConfigViolation
import com.willfp.libreforge.effects.Effect
import com.willfp.libreforge.triggers.TriggerData
import com.willfp.libreforge.triggers.TriggerParameter
import com.willfp.libreforge.triggers.Triggers
import org.bukkit.potion.PotionEffect
import org.bukkit.potion.PotionEffectType

class EffectPotionEffect : Effect(
    "potion_effect",
    applicableTriggers = Triggers.withParameters(
        TriggerParameter.PLAYER
    )
) {
    override fun handle(data: TriggerData, config: Config) {
        val toApply = if (config.getBool("apply_to_player")) {
            data.player ?: return
        } else {
            data.victim ?: return
        }

        toApply.addPotionEffect(
            PotionEffect(
                PotionEffectType.getByName(config.getString("effect").uppercase())
                    ?: PotionEffectType.INCREASE_DAMAGE,
                config.getIntFromExpression("duration", data.player),
                config.getIntFromExpression("level", data.player) - 1,
                plugin.configYml.getBool("potions.ambient.triggered"),
                plugin.configYml.getBool("potions.particles.triggered"),
                plugin.configYml.getBool("potions.icon.triggered")
            )
        )
    }

    override fun validateConfig(config: Config): List<ConfigViolation> {
        val violations = mutableListOf<ConfigViolation>()

        if (PotionEffectType.getByName(config.getStringOrNull("effect")?.uppercase() ?: "") == null) violations.add(
            ConfigViolation(
                "effect",
                "You must specify the potion effect / invalid effect specified! Get a list of valid effects here: "
                        + " https://hub.spigotmc.org/javadocs/bukkit/org/bukkit/potion/PotionEffectType.html"
            )
        )

        if (!config.has("level")) violations.add(
            ConfigViolation(
                "level",
                "You must specify the effect level!"
            )
        )

        if (!config.has("duration")) violations.add(
            ConfigViolation(
                "duration",
                "You must specify the duration!"
            )
        )

        if (!config.has("apply_to_player")) violations.add(
            ConfigViolation(
                "apply_to_player",
                "You must specify whether the player or victim gets the effect!"
            )
        )

        return violations
    }
}
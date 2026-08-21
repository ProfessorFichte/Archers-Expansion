package com.archers_expansion.entity.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.fx.Fx;
import net.spell_engine.fx.ModelEffectHelper;
import net.spell_engine.fx.ParticleHelper;
import net.spell_engine.internals.SpellExecution;
import net.spell_engine.internals.SpellParameters;
import net.spell_engine.internals.impact.SpellImpacts;
import net.spell_engine.utils.SoundHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;
import net.spell_engine.api.spell.registry.SpellRegistry;

public class SpellAreaExplosion {
    public static void trigger(Entity source, LivingEntity owner, Identifier spellId) {
        var spellEntry = SpellRegistry.from(owner.getWorld()).getEntry(spellId).orElse(null);
        if (spellEntry == null) return;
        var spell = spellEntry.value();

        // `release.visuals` merges what used to be `release.particles` and
        // `release.particles_scaled_with_ranged`; resolving against the spell's reach is what
        // applies `scale_with = RANGE` to the entries that ask for it.
        var releaseVisuals = spell.release.visuals
                .resolved(Fx.Context.ofRange(SpellParameters.getRange(owner, spellEntry)));
        ParticleHelper.sendBatches(source, releaseVisuals.particles);
        ModelEffectHelper.spawn(owner.getWorld(), source.getPos(), source.getYaw(), releaseVisuals.models);
        SoundHelper.playSound(owner.getWorld(),source,spell.release.sound);
        for (var target : TargetHelper.targetsFromArea(source, spell.range, spell.target.area, e -> e != source)) {
            SpellImpacts.performImpacts(owner.getWorld(), owner, target, owner, spellEntry,
                    spell.impacts, new SpellExecution.ImpactContext()
                            .power(SpellPower.getSpellPower(spell.school, owner))
                            .position(source.getPos()));
            ParticleHelper.sendBatches(target, spell.impacts.get(0).visuals.particles);
        }
    }
}

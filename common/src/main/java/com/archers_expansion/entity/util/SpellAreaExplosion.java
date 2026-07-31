package com.archers_expansion.entity.util;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.fx.ParticleHelper;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;
import net.spell_engine.api.spell.registry.SpellRegistry;

public class SpellAreaExplosion {
    public static void trigger(Entity source, LivingEntity owner, Identifier spellId) {
        var spellEntry = SpellRegistry.from(owner.getWorld()).getEntry(spellId).orElse(null);
        if (spellEntry == null) return;
        var spell = spellEntry.value();

        ParticleHelper.sendBatches(source, spell.release.particles);
        ParticleHelper.sendBatches(source, spell.release.particles_scaled_with_ranged);
        for (var target : TargetHelper.targetsFromArea(source, spell.range, spell.target.area, e -> e != source)) {
            SpellHelper.performImpacts(owner.getWorld(), owner, target, owner, spellEntry,
                    spell.impacts, new SpellHelper.ImpactContext()
                            .power(SpellPower.getSpellPower(spell.school, owner))
                            .position(source.getPos()));
            ParticleHelper.sendBatches(target, spell.impacts.get(0).particles);
        }
    }
}

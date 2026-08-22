package com.archers_expansion.effect;

import com.archers_expansion.ArchersExpansionMod;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.effect.StealthStatusEffect;
import net.more_rpg_classes.sounds.MRPGLibSounds;
import net.spell_engine.api.spell.fx.ParticleGroup;
import net.spell_engine.api.spell.fx.ParticleGroupBuilder;
import net.spell_engine.fx.SpellEngineParticles;

public class InfiltratorsVanishEffect extends StealthStatusEffect{
    protected InfiltratorsVanishEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    private static final ParticleGroup POP_PARTICLES = ParticleGroupBuilder.of(SpellEngineParticles.smoke_medium)
            .batch(b -> b.shape(ParticleGroup.Shape.CIRCLE)
                    .count(20)
                    .speed(0.18F, 0.2F)
                    .verticalOrigin(ParticleGroupBuilder.Batches.FEET));
    private static final Identifier LEAVE_SOUND_ID = MRPGLibSounds.STEALTH_VANISH.id();

    @Override
    public ParticleGroup stealthPopParticles() {
        return POP_PARTICLES;
    }

    @Override
    public Identifier stealthLeaveSoundId() {
        return LEAVE_SOUND_ID;
    }

    @Override
    public double stealthFollowRange() {
        return ArchersExpansionMod.tweaksConfig.value.stealth_follow_range;
    }

    @Override
    public double stealthVisibilityMultiplier() {
        return ArchersExpansionMod.tweaksConfig.value.stealth_visibility_multiplier;
    }
}

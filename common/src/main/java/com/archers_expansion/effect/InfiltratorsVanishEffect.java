package com.archers_expansion.effect;

import com.archers_expansion.ArchersExpansionMod;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.effect.StealthStatusEffect;
import net.more_rpg_classes.sounds.MRPGLibSounds;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.fx.SpellEngineParticles;

public class InfiltratorsVanishEffect extends StealthStatusEffect{
    protected InfiltratorsVanishEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    private static final ParticleBatch POP_PARTICLES = new ParticleBatch(
            SpellEngineParticles.smoke_medium.id().toString(),
            ParticleBatch.Shape.CIRCLE,
            ParticleBatch.Origin.FEET,
            null,
            20,
            0.18F,
            0.2F,
            0);
    private static final Identifier LEAVE_SOUND_ID = MRPGLibSounds.STEALTH_VANISH.id();

    @Override
    public ParticleBatch stealthPopParticles() {
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

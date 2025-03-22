package com.archers_expansion.effect;

import com.archers_expansion.ArchersExpansionMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.SoundHelper;

public class InfiltratorsArrowEffect extends StatusEffect {
    protected InfiltratorsArrowEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    public static final ParticleBatch POP_PARTICLES = new ParticleBatch(
            "spell_engine:smoke_medium",
            ParticleBatch.Shape.CIRCLE,
            ParticleBatch.Origin.FEET,
            null,
            20,
            0.18F,
            0.2F,
            0);
    public static final Identifier LEAVE_SOUND_ID = new Identifier(ArchersExpansionMod.MOD_ID, "infiltrator_vanish");
    public static final SoundEvent LEAVE_SOUND = SoundEvent.of(LEAVE_SOUND_ID);

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        super.onRemoved(entity, attributes, amplifier);
        if (!entity.getWorld().isClient()) {
            SoundHelper.playSoundEvent(entity.getWorld(), entity, LEAVE_SOUND);
            ParticleHelper.sendBatches(entity, new ParticleBatch[]{ POP_PARTICLES });
        }
    }
}

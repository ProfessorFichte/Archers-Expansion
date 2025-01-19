package com.archers_expansion.client;

import net.minecraft.entity.LivingEntity;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;

public class PinDownParticles implements CustomParticleStatusEffect.Spawner{
    private final ParticleBatch particles;

    public PinDownParticles(int particleCount) {
        this.particles = new ParticleBatch(
                "spell_engine:weakness_smoke",
                ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                null, particleCount, 0.1F, 0.2F, 360);
    }

    @Override
    public void spawnParticles(LivingEntity livingEntity, int amplifier) {
        var scaledParticles = new ParticleBatch(particles);
        scaledParticles.count = (1);
        ParticleHelper.play(livingEntity.getWorld(), livingEntity, scaledParticles);
    }
}

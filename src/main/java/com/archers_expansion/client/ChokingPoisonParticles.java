package com.archers_expansion.client;

import net.minecraft.entity.LivingEntity;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;

public class ChokingPoisonParticles implements CustomParticleStatusEffect.Spawner{
    private final ParticleBatch particles;

    public ChokingPoisonParticles(int particleCount) {
        this.particles = new ParticleBatch(
                "more_rpg_classes:gas_cloud",
                ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.LAUNCH_POINT,
                null, particleCount, 0.05F, 0.1F, 360);
    }

    @Override
    public void spawnParticles(LivingEntity livingEntity, int amplifier) {
        var scaledParticles = new ParticleBatch(particles);
        scaledParticles.count = (1);
        ParticleHelper.play(livingEntity.getWorld(), livingEntity, scaledParticles);
    }
}

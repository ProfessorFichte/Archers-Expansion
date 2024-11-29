package com.archers_expansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.Vec3d;

public class LeapingShotEffect extends StatusEffect {
    protected LeapingShotEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        if (!entity.getWorld().isClient && !entity.isSneaking()) {
            float speed_leaping = -1.25F;
            float leaping_height = 0.65F;
            entity.velocityDirty = true;
            entity.velocityModified = true;
            Vec3d rotationVector = entity.getRotationVector();
            Vec3d velocity = entity.getVelocity();
            entity.addVelocity(rotationVector.x * 0.1 + (rotationVector.x * 2.5 - velocity.x) * speed_leaping,
                    leaping_height, rotationVector.z * 0.1 + (rotationVector.z * 2.5 - velocity.z) * speed_leaping);
        }

    }
}

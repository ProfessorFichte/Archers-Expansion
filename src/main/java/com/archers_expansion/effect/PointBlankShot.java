package com.archers_expansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.SpellInfo;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;
import static net.spell_engine.internals.SpellRegistry.getSpell;

public class PointBlankShot extends StatusEffect {
    protected PointBlankShot(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient) {
            LivingEntity attacker = entity.getLastAttacker();
            entity.velocityDirty = true;
            entity.velocityModified = true;
            Vec3d rotationVector = entity.getRotationVector();
            Vec3d velocity = entity.getVelocity();

            float range = getSpell(Identifier.of(MOD_ID, "point_blank_shot")).range;
            int poscasterX = attacker.getBlockPos().getX();
            int poscasterZ = attacker.getBlockPos().getZ();
            int posentityX = entity.getBlockPos().getX();
            int posentityZ = entity.getBlockPos().getZ();
            int distance_to_target = (posentityX-poscasterX) + ( poscasterZ - posentityZ);
            float diff_calc = (range - distance_to_target)/50;

            float speed_leaping = -0.5F - diff_calc;
            float leaping_height = 0.5F;

            entity.velocityDirty = true;
            entity.velocityModified = true;
            entity.addVelocity(rotationVector.x * 0.1 + (rotationVector.x * 2.5 - velocity.x) * speed_leaping,
                 leaping_height, rotationVector.z * 0.1 + (rotationVector.z * 2.5 - velocity.z) * speed_leaping);

        }
    }
}

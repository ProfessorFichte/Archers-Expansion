package com.archers_expansion.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.tag.EntityTypeTags;
import net.more_rpg_classes.util.CustomMethods;

public class WintersGraspEffect extends StatusEffect {
    protected WintersGraspEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    public boolean applyUpdateEffect(LivingEntity livingEntity, int pAmplifier) {
        EntityType<?> type = livingEntity.getType();
        if(!type.isIn(EntityTypeTags.FREEZE_IMMUNE_ENTITY_TYPES)) {
            CustomMethods.freezeDamageTicks(livingEntity);
        }
        return true;
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;

    }


}

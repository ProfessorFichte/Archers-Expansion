package com.archers_expansion.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.tag.EntityTypeTags;
import net.more_rpg_classes.effect.MRPGCEffects;

public class ChokingGasEffect extends StatusEffect {

    protected ChokingGasEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        EntityType<?> type = entity.getType();
        if(type.isIn(EntityTypeTags.SKELETONS)){
            entity.removeStatusEffect(Effects.CHOKING_GAS);
        }
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        float damage = 1.0F;
        if(entity.hasStatusEffect(MRPGCEffects.BLEEDING)){
            damage = damage + 0.5F;
        }
        entity.damage(entity.getDamageSources().magic(), damage);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i;
        i = 30 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}

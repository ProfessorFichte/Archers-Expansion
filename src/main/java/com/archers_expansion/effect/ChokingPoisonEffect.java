package com.archers_expansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.more_rpg_classes.effect.MRPGCEffects;

public class ChokingPoisonEffect extends StatusEffect {
    protected ChokingPoisonEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }
    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        float damage = 1.0F;
        if(entity.hasStatusEffect(MRPGCEffects.BLEEDING.registryEntry)){
            damage = damage + 0.5F;
        }
        entity.damage(entity.getDamageSources().magic(), damage);
        return true;
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

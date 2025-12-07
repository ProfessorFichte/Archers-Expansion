package com.archers_expansion.effect;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.tag.EntityTypeTags;
import net.more_rpg_classes.effect.MRPGCEffects;

public class ChokingGasEffect extends StatusEffect {

    protected ChokingGasEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        EntityType<?> type = entity.getType();
        if(type.isIn(EntityTypeTags.IGNORES_POISON_AND_REGEN)){
            entity.removeStatusEffect(Effects.CHOKING_GAS.registryEntry);
        }
        super.onApplied(entity, amplifier);
    }

    public boolean applyUpdateEffect(LivingEntity entity, int amplifier) {
        float damage = amplifier + 1.0F;
        entity.damage(entity.getDamageSources().magic(), damage);
        return true;
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int interval = 40 >> amplifier;
        if (interval < 25) {
            interval = 25;
        }
        return duration % interval == 0;
    }
}

package com.archers_expansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.more_rpg_classes.effect.MRPGCEffects;

public class FrozenPactEffect extends StatusEffect {
    protected FrozenPactEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        if(entity.getFrozenTicks()== 0){
            entity.removeStatusEffect(Effects.FROZEN_PACT.registryEntry);
        }else{
            float damage = ((float) entity.getFrozenTicks() / 150);
            entity.damage(entity.getDamageSources().freeze(), 1.0F + damage);
            entity.setFrozenTicks(0);
        }
        if(entity.hasStatusEffect(MRPGCEffects.FROSTED.registryEntry)){
            entity.removeStatusEffect(MRPGCEffects.FROSTED.registryEntry);
        }

    }
}

package com.archers_expansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CrystalArrowEffect extends StatusEffect {
    protected CrystalArrowEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        int frozen_ticks = entity.getFrozenTicks();
        if(frozen_ticks < 140){
            entity.setFrozenTicks(frozen_ticks + 40);
        }
    }
}

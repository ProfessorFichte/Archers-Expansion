package com.archers_expansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class CrystalArrowEffect extends StatusEffect {
    protected CrystalArrowEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        int ticks = ((amplifier + 1) * 100) + 300;
        entity.setFrozenTicks(entity.getFrozenTicks()+ticks);

    }
}

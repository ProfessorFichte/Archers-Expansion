package com.archers_expansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class FrozenPactEffect extends StatusEffect {
    protected FrozenPactEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        int frozen_ticks = entity.getFrozenTicks();
        if(!(frozen_ticks == 0)){
            float damage_multiplicator = (float) frozen_ticks /100;
            int damage = (int) ((amplifier + 2) * damage_multiplicator);
            entity.damage(entity.getDamageSources().freeze(), 1.0F + damage);

        }
    }
}

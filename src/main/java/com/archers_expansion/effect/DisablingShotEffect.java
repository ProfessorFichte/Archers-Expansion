package com.archers_expansion.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.more_rpg_classes.damage.BleedingDamageSource;

public class DisablingShotEffect extends StatusEffect {
    protected DisablingShotEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        entity.damage(new BleedingDamageSource(entity.getDamageSources().starve().getTypeRegistryEntry()), 1.0F);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i;
        i = 35 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
            }
        return true;
    }
}

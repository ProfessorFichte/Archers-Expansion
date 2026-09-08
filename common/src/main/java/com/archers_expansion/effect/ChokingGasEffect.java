package com.archers_expansion.effect;

import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;

public class ChokingGasEffect extends StatusEffect {

    protected ChokingGasEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        // `#minecraft:ignores_poison_and_regen` is a 1.21 tag; on 1.20.1 poison immunity is the
        // hardcoded undead check that vanilla's own poison effect uses.
        if (entity.isUndead()) {
            entity.removeStatusEffect(ArchersExpansionEffects.CHOKING_GAS.effect);
        }
        super.onApplied(entity, attributes, amplifier);
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        float damage = amplifier + 1.0F;
        entity.damage(entity.getDamageSources().magic(), damage);
        int interval = Math.max(25, 40 >> amplifier);
        entity.setAir(entity.getAir() - interval);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int interval = 40 >> amplifier;
        if (interval < 25) {
            interval = 25;
        }
        return duration % interval == 0;
    }
}

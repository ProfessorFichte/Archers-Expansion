package com.archers_expansion.effect;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;
import static net.spell_engine.internals.SpellRegistry.getSpell;

public class PointBlankShot extends StatusEffect {
    protected PointBlankShot(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        if (!entity.getWorld().isClient) {
            LivingEntity attacker = entity.getLastAttacker();

            float range = getSpell(Identifier.of(MOD_ID, "point_blank_shot")).range;
            int poscasterX = attacker.getBlockPos().getX();
            int poscasterZ = attacker.getBlockPos().getZ();
            int posentityX = entity.getBlockPos().getX();
            int posentityZ = entity.getBlockPos().getZ();
            int distance_to_target = (posentityX-poscasterX) + ( poscasterZ - posentityZ);

            float diff_calc = (range - distance_to_target)/5;
            var level = EnchantmentHelper.getLevel(Enchantments.POWER, attacker.getMainHandStack());
            float knockback = 0.1F + diff_calc + level;

            double d = attacker.getX() - entity.getX();
            double e;
            for(e = attacker.getZ() - entity.getZ(); d * d + e * e < 1.0E-4; e = (Math.random() - Math.random()) * 0.01) {
                d = (Math.random() - Math.random()) * 0.01;
            }
            entity.takeKnockback(knockback, d, e);
        }
    }
}

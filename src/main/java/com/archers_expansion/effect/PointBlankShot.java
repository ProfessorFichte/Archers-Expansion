package com.archers_expansion.effect;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.registry.SpellRegistry;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class PointBlankShot extends StatusEffect {
    protected PointBlankShot(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, int amplifier) {
        super.onApplied(entity, amplifier);
        if (!entity.getWorld().isClient) {
            LivingEntity attacker = entity.getLastAttacker();
            var spellEntry = SpellRegistry.from(attacker.getWorld()).getEntry(Identifier.of(MOD_ID, "point_blank_shot")).orElse(null);
            var spell = spellEntry.value();
            float range = spell.range;
            int poscasterX = attacker.getBlockPos().getX();
            int poscasterZ = attacker.getBlockPos().getZ();
            int posentityX = entity.getBlockPos().getX();
            int posentityZ = entity.getBlockPos().getZ();
            int distance_to_target = (posentityX-poscasterX) + ( poscasterZ - posentityZ);

            float diff_calc = (range - distance_to_target)/5;
            float power_ench = 0.0F;
            var power_enchantment = attacker.getWorld().getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(Enchantments.PUNCH);

            if (power_enchantment.isPresent()) {
                power_ench = EnchantmentHelper.getLevel(power_enchantment.get(), attacker.getMainHandStack());
            }
            float knockback = 0.1F + diff_calc + power_ench;

            double d = attacker.getX() - entity.getX();
            double e;
            for(e = attacker.getZ() - entity.getZ(); d * d + e * e < 1.0E-4; e = (Math.random() - Math.random()) * 0.01) {
                d = (Math.random() - Math.random()) * 0.01;
            }
            entity.takeKnockback(knockback, d, e);
        }
    }
}

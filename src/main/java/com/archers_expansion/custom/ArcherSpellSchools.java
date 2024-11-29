package com.archers_expansion.custom;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.spell_power.SpellPowerMod;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;


public class ArcherSpellSchools {
    public static final SpellSchool FROST_RANGED = new SpellSchool(SpellSchool.Archetype.ARCHERY,
            Identifier.of(SpellPowerMod.ID, "frost_ranged"),
            SpellSchools.FROST.color,
            DamageTypes.ARROW,
            EntityAttributes_RangedWeapon.DAMAGE.entry
    );

    public static void registerSchools() {
        FROST_RANGED.addSource(SpellSchool.Trait.POWER, SpellSchool.Apply.ADD, query -> {
            var power = query.entity().getAttributeValue(EntityAttributes_RangedWeapon.DAMAGE.entry)
                    + query.entity().getAttributeValue(SpellSchools.FROST.attributeEntry);
            var world = query.entity().getWorld();
            var power_enchantment = world.getRegistryManager().get(RegistryKeys.ENCHANTMENT).getEntry(Enchantments.POWER);
            if (power_enchantment.isPresent()) {
                var level = EnchantmentHelper.getLevel(power_enchantment.get(), query.entity().getMainHandStack());
                power *= 1 + (0.05 * level);
            }
            return power;
        });
        FROST_RANGED.addSource(SpellSchool.Trait.HASTE, new SpellSchool.Source(SpellSchool.Apply.ADD, query ->  {
            var haste = query.entity().getAttributeValue(EntityAttributes_RangedWeapon.HASTE.entry);
            var rate = EntityAttributes_RangedWeapon.HASTE.asMultiplier(haste);
            return rate - 1;
        }));

        SpellSchools.register(FROST_RANGED);

    }
}

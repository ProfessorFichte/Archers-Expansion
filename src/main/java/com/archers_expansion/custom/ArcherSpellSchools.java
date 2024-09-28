package com.archers_expansion.custom;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.util.Identifier;
import net.spell_power.SpellPowerMod;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;

public class ArcherSpellSchools {
    public static final SpellSchool FROST_RANGED = new SpellSchool(SpellSchool.Archetype.ARCHERY,
            new Identifier(SpellPowerMod.ID, "frost_ranged"),
            SpellSchools.FROST.color,
            DamageTypes.ARROW,
            EntityAttributes_RangedWeapon.DAMAGE.attribute
    );

    public static void registerSchools() {
        FROST_RANGED.attributeManagement = SpellSchool.Manage.EXTERNAL;
            FROST_RANGED.addSource(SpellSchool.Trait.POWER, SpellSchool.Apply.ADD, query -> {
                var power = query.entity().getAttributeValue(EntityAttributes_RangedWeapon.DAMAGE.attribute)
                        + query.entity().getAttributeValue(SpellSchools.FROST.attribute);
                var level = EnchantmentHelper.getLevel(Enchantments.POWER, query.entity().getMainHandStack());
                power *= 1 + (0.05 * level);
                return power;
            });
        FROST_RANGED.addSource(SpellSchool.Trait.HASTE, SpellSchool.Apply.ADD, query -> {
                var haste = query.entity().getAttributeValue(EntityAttributes_RangedWeapon.HASTE.attribute);
                var rate = EntityAttributes_RangedWeapon.HASTE.asMultiplier(haste);
                return rate - 1;
            });

        SpellSchools.register(FROST_RANGED);

    }
}

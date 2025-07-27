package com.archers_expansion.custom;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.damage.DamageTypes;
import net.minecraft.util.Identifier;
import net.spell_power.SpellPowerMod;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.SpellSchools;

public class ArcherySchools {
    public static final SpellSchool FROST_RANGED = new SpellSchool(SpellSchool.Archetype.ARCHERY,
            Identifier.of(SpellPowerMod.ID, "frost_ranged"),
            0xccffff,
            DamageTypes.ARROW,
            EntityAttributes_RangedWeapon.DAMAGE.attribute);
    public static final SpellSchool FIRE_RANGED = new SpellSchool(SpellSchool.Archetype.ARCHERY,
            Identifier.of(SpellPowerMod.ID, "fire_ranged"),
            0xff3300,
            DamageTypes.ARROW,
            EntityAttributes_RangedWeapon.DAMAGE.attribute);

    public static void initialize() {
        FROST_RANGED.addSource(SpellSchool.Trait.POWER, SpellSchool.Apply.ADD, query -> {
            var second_power = query.entity().getAttributeValue(SpellSchools.FROST.attribute);
            var power = query.entity().getAttributeValue(EntityAttributes_RangedWeapon.DAMAGE.attribute) + second_power;
            var level = EnchantmentHelper.getLevel(Enchantments.POWER, query.entity().getMainHandStack());
            power *= 1 + (0.05 * level);
            return power;
        });
        FROST_RANGED.addSource(SpellSchool.Trait.HASTE, SpellSchool.Apply.ADD, query -> {
            var haste = query.entity().getAttributeValue(EntityAttributes_RangedWeapon.HASTE.attribute);
            var rate = EntityAttributes_RangedWeapon.HASTE.asMultiplier(haste);
            return rate - 1;
        });
        SpellSchools.configureSpellHaste(FROST_RANGED);
        SpellSchools.register(FROST_RANGED);

        FIRE_RANGED.addSource(SpellSchool.Trait.POWER, SpellSchool.Apply.ADD, query -> {
            var second_power = query.entity().getAttributeValue(SpellSchools.FIRE.attribute);
            var power = query.entity().getAttributeValue(EntityAttributes_RangedWeapon.DAMAGE.attribute) + second_power;
            var level = EnchantmentHelper.getLevel(Enchantments.POWER, query.entity().getMainHandStack());
            power *= 1 + (0.05 * level);
            return power;
        });
        FIRE_RANGED.addSource(SpellSchool.Trait.HASTE, SpellSchool.Apply.ADD, query -> {
            var haste = query.entity().getAttributeValue(EntityAttributes_RangedWeapon.HASTE.attribute);
            var rate = EntityAttributes_RangedWeapon.HASTE.asMultiplier(haste);
            return rate - 1;
        });
        SpellSchools.configureSpellHaste(FIRE_RANGED);
        SpellSchools.register(FIRE_RANGED);
    }
}

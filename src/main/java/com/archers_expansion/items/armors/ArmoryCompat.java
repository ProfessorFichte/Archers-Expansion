package com.archers_expansion.items.armors;

import com.archers_expansion.items.Group;
import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.config.ArmorSetConfig;
import net.spell_engine.api.config.AttributeModifier;
import net.spell_engine.api.entity.SpellEngineAttributes;
import net.spell_engine.api.item.Equipment;
import net.spell_engine.api.item.armor.Armor;
import net.spell_engine.api.spell.SpellDataComponents;
import net.spell_power.api.SpellPowerMechanics;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ArmoryCompat {
    public static final ArrayList<Armor.Entry> entries = new ArrayList<>();
    private static Armor.Entry create(RegistryEntry<ArmorMaterial> material, Identifier id, int durability, int tier,
                                      Armor.Set.ItemFactory factory, ArmorSetConfig defaults, Armor.ItemSettingsTweaker settings) {
        var entry = Armor.Entry.create(
                material,
                id,
                durability,
                factory,
                defaults,
                Equipment.LootProperties.of(tier),
                settings
        );
        entries.add(entry);
        return entry;
    }

    public static RegistryEntry<ArmorMaterial> material(
            String name, int protectionHead, int protectionChest, int protectionLegs, int protectionFeet,
            int enchantability, RegistryEntry<SoundEvent> equipSound, Supplier<Ingredient> repairIngredient) {

        var material = new ArmorMaterial(
                Map.of(
                        ArmorItem.Type.HELMET, protectionHead,
                        ArmorItem.Type.CHESTPLATE, protectionChest,
                        ArmorItem.Type.LEGGINGS, protectionLegs,
                        ArmorItem.Type.BOOTS, protectionFeet),
                enchantability, equipSound, repairIngredient,
                List.of(new ArmorMaterial.Layer(Identifier.of(MOD_ID, name))),
                0,0
        );
        return Registry.registerReference(Registries.ARMOR_MATERIAL, Identifier.of(MOD_ID, name), material);
    }
    public static Identifier bounty_hunter_passive = Identifier.of(MOD_ID, "bounty_hunter");
    public static Identifier polar_stalker_passive = Identifier.of(MOD_ID, "polar_stalker");
    public static Identifier sentinel_archer_passive = Identifier.of(MOD_ID, "sentinel_archer");

    public static RegistryEntry<ArmorMaterial> material_bounty_hunter = material(
            "bounty_hunter",
            2, 4, 4, 2,
            18,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });
    public static RegistryEntry<ArmorMaterial> material_polar_stalker = material(
            "polar_stalker",
            2, 4, 4, 2,
            18,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });
    public static RegistryEntry<ArmorMaterial> material_sentinel_archer = material(
            "sentinel_archer",
            3, 5, 4, 3,
            18,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });


    public static final float war_archer_damage = 0.14F;
    public static final float war_archer_velocity = 0.25F;
    public static final float war_archer_knockback = 0.1F;

    public static final float deadeye_damage = 0.09F;
    public static final float deadeye_evasion = 0.05F;
    public static final float deadeye_haste = 0.07F;

    public static final float tundra_hunter_spell_power = 1.5F;
    public static final float tundra_hunter_haste = 0.05F;
    public static final float tundra_hunter_damage = 0.1F;


    public static final int durability = 40;

    private static Armor.ItemSettingsTweaker commonSettings(Identifier equipmentSetId) {
        return Armor.ItemSettingsTweaker.standard(itemSettings -> {
            itemSettings
                    .component(SpellDataComponents.EQUIPMENT_SET, equipmentSetId)
                    .component(DataComponentTypes.RARITY, Rarity.RARE);
        });
    }

    private static final Identifier RANGED_HASTE_ID = Identifier.of(EntityAttributes_RangedWeapon.HASTE.id.toString());
    private static final Identifier RANGED_DAMAGE_ID = Identifier.of(EntityAttributes_RangedWeapon.DAMAGE.id.toString());
    private static final Identifier RANGED_VELOCITY_ID = Identifier.of(EntityAttributes_RangedWeapon.VELOCITY.id.toString());
    private static final Identifier KNOCKBACK_ID = Identifier.ofVanilla("generic.knockback_resistance");

    public static final Armor.Entry bounty_hunter = create(
            material_bounty_hunter,
            Identifier.of(MOD_ID, "bounty_hunter"),
            durability,
            5,
            Armor.CustomItem::new,
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(2)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, deadeye_damage))
                            .add(AttributeModifier.multiply(RANGED_HASTE_ID, deadeye_haste))
                            .add(AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id, deadeye_evasion)),
                    new ArmorSetConfig.Piece(4)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, deadeye_damage))
                            .add(AttributeModifier.multiply(RANGED_HASTE_ID, deadeye_haste))
                            .add(AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id, deadeye_evasion)),
                    new ArmorSetConfig.Piece(4)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, deadeye_damage))
                            .add(AttributeModifier.multiply(RANGED_HASTE_ID, deadeye_haste))
                            .add(AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id, deadeye_evasion)),
                    new ArmorSetConfig.Piece(2)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, deadeye_damage))
                            .add(AttributeModifier.multiply(RANGED_HASTE_ID, deadeye_haste))
                            .add(AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id, deadeye_evasion))
            ),
            commonSettings(bounty_hunter_passive))
            .translatedName("", "", "", "");
    public static final Armor.Entry polar_stalker = create(
            material_polar_stalker,
            Identifier.of(MOD_ID, "polar_stalker"),
            durability,
            5,
            Armor.CustomItem::new,
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(2)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, tundra_hunter_damage))
                            .add(AttributeModifier.multiply(RANGED_HASTE_ID, tundra_hunter_haste))
                            .add(AttributeModifier.bonus(SpellSchools.FROST.id, tundra_hunter_spell_power)),
                    new ArmorSetConfig.Piece(4)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, tundra_hunter_damage))
                            .add(AttributeModifier.multiply(RANGED_HASTE_ID, tundra_hunter_haste))
                            .add(AttributeModifier.bonus(SpellSchools.FROST.id, tundra_hunter_spell_power)),
                    new ArmorSetConfig.Piece(4)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, tundra_hunter_damage))
                            .add(AttributeModifier.multiply(RANGED_HASTE_ID, tundra_hunter_haste))
                            .add(AttributeModifier.bonus(SpellSchools.FROST.id, tundra_hunter_spell_power)),
                    new ArmorSetConfig.Piece(2)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, tundra_hunter_damage))
                            .add(AttributeModifier.multiply(RANGED_HASTE_ID, tundra_hunter_haste))
                            .add(AttributeModifier.bonus(SpellSchools.FROST.id, tundra_hunter_spell_power))
                    ),
            commonSettings(polar_stalker_passive))
            .translatedName("", "", "", "");
    public static final Armor.Entry sentinel_archer = create(
            material_sentinel_archer,
            Identifier.of(MOD_ID, "sentinel_archer"),
            durability,
            5,
            Armor.CustomItem::new,
            ArmorSetConfig.with(
                    new ArmorSetConfig.Piece(3)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, war_archer_damage))
                            .add(AttributeModifier.multiply(KNOCKBACK_ID, war_archer_knockback))
                            .add(AttributeModifier.multiply(RANGED_VELOCITY_ID, war_archer_velocity)),
                    new ArmorSetConfig.Piece(5)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, war_archer_damage))
                            .add(AttributeModifier.multiply(KNOCKBACK_ID, war_archer_knockback))
                            .add(AttributeModifier.multiply(RANGED_VELOCITY_ID, war_archer_velocity)),
                    new ArmorSetConfig.Piece(4)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, war_archer_damage))
                            .add(AttributeModifier.multiply(KNOCKBACK_ID, war_archer_knockback))
                            .add(AttributeModifier.multiply(RANGED_VELOCITY_ID, war_archer_velocity)),
                    new ArmorSetConfig.Piece(3)
                            .add(AttributeModifier.multiply(RANGED_DAMAGE_ID, war_archer_damage))
                            .add(AttributeModifier.multiply(KNOCKBACK_ID, war_archer_knockback))
                            .add(AttributeModifier.multiply(RANGED_VELOCITY_ID, war_archer_velocity))
            ),
            commonSettings(sentinel_archer_passive))
            .translatedName("", "", "", "");

    public static void register(Map<String, ArmorSetConfig> configs) {
        Armor.register(configs, entries, Group.KEY);
    }
}

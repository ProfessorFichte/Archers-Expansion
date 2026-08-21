package com.archers_expansion.items;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Rarity;
import net.more_rpg_classes.item.MRPGCItemGroups;
import net.more_rpg_classes.item.MRPGCItems;
import net.spell_engine.rpg_series.config.ArmorSetConfig;
import net.spell_engine.rpg_series.config.AttributeModifier;
import net.spell_engine.api.entity.SpellEngineAttributes;
import net.spell_engine.rpg_series.item.Equipment;
import net.spell_engine.rpg_series.item.Armor;
import net.spell_engine.api.spell.SpellDataComponents;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;
import static com.archers_expansion.compat.CompatLoadingCheck.armoryLoadCheck;

public class Armors {
    private static final Supplier<Ingredient> TUNDRA_INGREDIENTS = () -> Ingredient.ofItems(
            MRPGCItems.POLAR_BEAR_FUR
    );
    private static final Supplier<Ingredient> WAT_ARCHER_INGREDIENTS = () -> Ingredient.ofItems(
            Items.IRON_INGOT,Items.CHAIN
    );
    private static final Supplier<Ingredient> DEADEYE_INGREDIENTS = () -> Ingredient.ofItems(
            MRPGCItems.HARDENED_LEATHER, Items.LEATHER
    );

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
    private static final Identifier ARMOR_TOUGHNESS_ID = Identifier.ofVanilla("generic.armor_toughness");

    private static final String CRIT_MOD_ID = "critical_strike";
    private static final Identifier CRIT_CHANCE_ID = Identifier.of(CRIT_MOD_ID, "chance");
    private static final Identifier CRIT_DAMAGE_ID = Identifier.of(CRIT_MOD_ID, "damage");

    public static final float tundra_ranged_damage_t2 = 0.06F;
    public static final float tundra_haste_t2 = 0.04F;
    private static final float tundra_t2_spell_power = 1.0F;
    public static final float tundra_ranged_damage_t3 = 0.07F;
    public static final float tundra_haste_t3 = 0.04F;
    private static final float tundra_t3_spell_power = 1F;
    public static final float tundra_ranged_damage_t5 = 0.07F;
    public static final float tundra_haste_t5 = 0.04F;
    private static final float tundra_t5_spell_power = 1.5F;

    public static final float war_archer_damage_t2 = 0.10F;
    public static final float war_archer_velocity_t2 = 0.15F;
    public static final float war_archer_t2_crit_damage = 0.04F;
    public static final float war_archer_damage_t3 = 0.12F;
    public static final float war_archer_velocity_t3 = 0.25F;
    public static final float war_archer_t3_toughness = 1.0F;
    public static final float war_archer_t3_crit_damage = 0.05F;
    public static final float war_archer_damage_t5 = 0.13F;
    public static final float war_archer_velocity_t5 = 0.25F;
    public static final float war_archer_t5_toughness = 1.0F;
    public static final float war_archer_t5_crit_damage = 0.06F;

    public static final float deadeye_damage_t2 = 0.05F;
    public static final float deadeye_evasion_t2 = 0.04F;
    public static final float deadeye_haste_t2 = 0.05F;
    public static final float deadeye_t2_crit_chance = 0.02F;
    public static final float deadeye_damage_t3 = 0.06F;
    public static final float deadeye_evasion_t3 = 0.05F;
    public static final float deadeye_haste_t3 = 0.07F;
    public static final float deadeye_t3_crit_chance = 0.025F;
    public static final float deadeye_damage_t5 = 0.06F;
    public static final float deadeye_evasion_t5 = 0.05F;
    public static final float deadeye_haste_t5 = 0.08F;
    public static final float deadeye_t5_crit_chance = 0.03F;

    public static RegistryEntry<ArmorMaterial> material(String name,
                                                        int protectionHead, int protectionChest, int protectionLegs, int protectionFeet,
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

    public static RegistryEntry<ArmorMaterial> material_tundra_hunter = material(
            "tundra_hunter",
            2, 3, 3, 2,
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, TUNDRA_INGREDIENTS);

    public static RegistryEntry<ArmorMaterial> material_war_archer = material(
            "war_archer",
            3, 5, 4, 3,
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, WAT_ARCHER_INGREDIENTS);
    public static RegistryEntry<ArmorMaterial> material_deadeye = material(
            "deadeye",
            2, 3, 3, 2,
            10,
            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER, DEADEYE_INGREDIENTS);
    public static RegistryEntry<ArmorMaterial> material_netherite_tundra_hunter = material(
            "netherite_tundra_hunter",
            2, 3, 3, 2,
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });
    public static RegistryEntry<ArmorMaterial> material_netherite_war_archer = material(
            "netherite_war_archer",
            3, 5, 4, 3,
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });
    public static RegistryEntry<ArmorMaterial> material_netherite_deadeye = material(
            "netherite_deadeye",
            2, 3, 3, 2,
            15,
            SoundEvents.ITEM_ARMOR_EQUIP_NETHERITE, () -> { return Ingredient.ofItems(Items.NETHERITE_INGOT); });
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

    public static final ArrayList<Armor.Entry> entries = new ArrayList<>();
    private static Armor.Entry create(RegistryEntry<ArmorMaterial> material, Identifier id, int durability,
                                      Armor.Set.ItemFactory factory, ArmorSetConfig defaults, int tier, Armor.ItemSettingsTweaker settings) {
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

    private static final Map<Armor.Entry, RegistryKey<ItemGroup>> groupOverrides = new IdentityHashMap<>();

    private static Armor.Entry groupKey(Armor.Entry entry, RegistryKey<ItemGroup> key) {
        groupOverrides.put(entry, key);
        return entry;
    }

    public static final Armor.Entry tundra_hunter_t1 =
            create(
                    material_tundra_hunter,
                    Identifier.of(MOD_ID, "tundra_hunter"),
                            25,
                    ArchersExpansionArmor::tundra_hunter,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t2),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t2),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t2_spell_power)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t2),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t2),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t2_spell_power)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t2),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t2),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t2_spell_power)
                                    )),
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t2),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t2),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t2_spell_power)
                                    ))
                    ),2,null
            ).translatedName("Tundra Hood", "Tundra Tunic", "Tundra Leggings", "Tundra Boots");

    public static final Armor.Entry war_archer_t1 =
            create(
                    material_war_archer,
                    Identifier.of(MOD_ID, "war_archer"),
                    25,
                    ArchersExpansionArmor::war_archer,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t2)
                                    )).addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t2_crit_damage)
                                    )),
                            new ArmorSetConfig.Piece(5)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t2)
                                    )).addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t2_crit_damage)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t2)
                                    )).addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t2_crit_damage)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t2)
                                    )).addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t2_crit_damage)
                                    ))
                    ),2,null
            ).translatedName("Bowman Helmet", "Bowman Chest", "Bowman Leggings", "Bowman Boots");

    public static final Armor.Entry deadeye_t1 =
            create(
                    material_deadeye,
                    Identifier.of(MOD_ID, "deadeye"),
                    25,
                    ArchersExpansionArmor::deadeye,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t2),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t2)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t2_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t2)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t2),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t2)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t2_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t2)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t2),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t2)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t2_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t2)
                                    )),
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t2),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t2)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t2_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t2)
                                    ))
                    ),2,null
            ).translatedName("Deadeye Hood", "Deadeye Tunic", "Deadeye Leggings", "Deadeye Boots");

    public static final Armor.Entry netherite_tundra_hunter =
            create(
                    material_netherite_tundra_hunter,
                    Identifier.of(MOD_ID, "netherite_tundra_hunter"),
                    35,
                    ArchersExpansionArmor::tundra_hunter,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t3),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t3),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t3_spell_power)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t3),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t3),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t3_spell_power)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t3),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t3),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t3_spell_power)
                                    )),
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t3),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t3),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t3_spell_power)
                                    ))
                    ),3,null
            ).translatedName("Netherite Tundra Hood", "Netherite Tundra Tunic", "Netherite Tundra Leggings", "Netherite Tundra Boots");

    public static final Armor.Entry netherite_war_archer =
            create(
                    material_netherite_war_archer,
                    Identifier.of(MOD_ID, "netherite_war_archer"),
                    35,
                    ArchersExpansionArmor::war_archer,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t3),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t3),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t3_toughness)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t3),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t3_crit_damage),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t3_toughness)
                                    )),
                            new ArmorSetConfig.Piece(5)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t3),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t3),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t3_toughness)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t3),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t3_crit_damage),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t3_toughness)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t3),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t3),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t3_toughness)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t3),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t3_crit_damage),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t3_toughness)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t3),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t3),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t3_toughness)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t3),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t3_crit_damage),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t3_toughness)
                                    ))
                    ),3,null
            ).translatedName("Netherite Bowman Helmet", "Netherite Bowman Chest", "Netherite Bowman Leggings", "Netherite Bowman Boots");

    public static final Armor.Entry netherite_deadeye =
            create(
                    material_netherite_deadeye,
                    Identifier.of(MOD_ID, "netherite_deadeye"),
                    35,
                    ArchersExpansionArmor::deadeye,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t3),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t3),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t3)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t3),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t3_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t3)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t3),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t3),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t3)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t3),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t3_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t3)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t3),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t3),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t3)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t3),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t3_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t3)
                                    )),
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t3),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t3),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t3)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t3),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t3_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t3)
                                    ))
                    ),3,null
            ).translatedName("Netherite Deadeye Hood", "Netherite Deadeye Tunic", "Netherite Deadeye Leggings", "Netherite Deadeye Boots");

    public static Armor.Entry bountyHunterArmorSet;
    public static Armor.Entry polarStalkerArmorSet;
    public static Armor.Entry sentinelArcherArmorSet;

    public static Identifier bounty_hunter_passive = Identifier.of(MOD_ID, "bounty_hunter");
    public static Identifier polar_stalker_passive = Identifier.of(MOD_ID, "polar_stalker");
    public static Identifier sentinel_archer_passive = Identifier.of(MOD_ID, "sentinel_archer");

    public static void register(Map<String, ArmorSetConfig> configs) {
        if (armoryLoadCheck()) {
            bountyHunterArmorSet = groupKey(create(
                    material_bounty_hunter,
                    Identifier.of(MOD_ID, "bounty_hunter"),
                    40,
                    Armor.CustomItem::new,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t5),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t5),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t5)
                                    )).addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t5),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t5_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t5)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t5),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t5),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t5)
                                    )).addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t5),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t5_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t5)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t5),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t5),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t5)
                                    )).addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t5),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t5_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t5)
                                    )),
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t5),
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,deadeye_damage_t5),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t5)
                                    )).addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_HASTE_ID,deadeye_haste_t5),
                                            AttributeModifier.multiply(CRIT_CHANCE_ID,deadeye_t5_crit_chance),
                                            AttributeModifier.multiply(SpellEngineAttributes.EVASION_CHANCE.id,deadeye_evasion_t5)
                                    ))
                    ),5,
                    commonSettings(bounty_hunter_passive)
            ).translatedName("Bounty Hunter Hood", "Bounty Hunter Tunic", "Bounty Hunter Leggings", "Bounty Hunter Boots"), MRPGCItemGroups.ARMORY_KEY);
            polarStalkerArmorSet = groupKey(create(
                    material_polar_stalker,
                    Identifier.of(MOD_ID, "polar_stalker"),
                    40,
                    Armor.CustomItem::new,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t5),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t5),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t5_spell_power)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t5),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t5),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t5_spell_power)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t5),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t5),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t5_spell_power)
                                    )),
                            new ArmorSetConfig.Piece(2)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t5),
                                            AttributeModifier.multiply(RANGED_HASTE_ID,tundra_haste_t5),
                                            AttributeModifier.bonus(SpellSchools.FROST.id,tundra_t5_spell_power)
                                    ))
                    ),5,
                    commonSettings(polar_stalker_passive)
            ).translatedName("Polar Stalker Hood", "Polar Stalker Tunic", "Polar Stalker Leggings", "Polar Stalker Boots"), MRPGCItemGroups.ARMORY_KEY);
            sentinelArcherArmorSet = groupKey(create(
                    material_sentinel_archer,
                    Identifier.of(MOD_ID, "sentinel_archer"),
                    40,
                    Armor.CustomItem::new,
                    ArmorSetConfig.with(
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t5),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t5),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t5_toughness)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t5),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t5_crit_damage),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t5_toughness)
                                    )),
                            new ArmorSetConfig.Piece(5)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t5),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t5),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t5_toughness)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t5),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t5_crit_damage),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t5_toughness)
                                    )),
                            new ArmorSetConfig.Piece(4)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t5),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t5),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t5_toughness)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t5),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t5_crit_damage),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t5_toughness)
                                    )),
                            new ArmorSetConfig.Piece(3)
                                    .addAll(List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t5),
                                            AttributeModifier.bonus(RANGED_VELOCITY_ID,war_archer_velocity_t5),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t5_toughness)
                                    ))
                                    .addConditional(CRIT_MOD_ID, List.of(
                                            AttributeModifier.multiply(RANGED_DAMAGE_ID,war_archer_damage_t5),
                                            AttributeModifier.multiply(CRIT_DAMAGE_ID,war_archer_t5_crit_damage),
                                            AttributeModifier.bonus(ARMOR_TOUGHNESS_ID,war_archer_t5_toughness)
                                    ))
                    ),5,
                    commonSettings(sentinel_archer_passive)
            ).translatedName("Sentinel Archer Helmet", "Sentinel Archer Chest", "Sentinel Archer Leggings", "Sentinel Archer Boots"), MRPGCItemGroups.ARMORY_KEY);
        }
        Armor.register(configs, entries, Group.KEY);
        for (var override : groupOverrides.entrySet()) {
            var entry = override.getKey();
            var key = override.getValue();
            var pieces = entry.armorSet().pieces();
            ItemGroupEvents.modifyEntriesEvent(Group.KEY).register(content -> {
                content.getDisplayStacks().removeIf(stack -> pieces.stream().anyMatch(p -> stack.isOf((ArmorItem) p)));
                content.getSearchTabStacks().removeIf(stack -> pieces.stream().anyMatch(p -> stack.isOf((ArmorItem) p)));
            });
            ItemGroupEvents.modifyEntriesEvent(key).register(content -> {
                for (var piece : pieces) {
                    content.add((ArmorItem) piece);
                }
            });
        }
    }

}

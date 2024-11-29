package com.archers_expansion.items.armors;

import com.archers_expansion.items.Group;
import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
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
import net.more_rpg_classes.item.MRPGCItems;
import net.spell_engine.api.item.ItemConfig;
import net.spell_engine.api.item.armor.Armor;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class Armors {
    private static final Supplier<Ingredient> TUNDRA_INGREDIENTS = () -> Ingredient.ofItems(
            MRPGCItems.POLAR_BEAR_FUR
    );
    private static final Supplier<Ingredient> WAT_ARCHER_INGREDIENTS = () -> Ingredient.ofItems(
            MRPGCItems.POLAR_BEAR_FUR
    );
    private static final Supplier<Ingredient> DEADEYE_INGREDIENTS = () -> Ingredient.ofItems(
            MRPGCItems.POLAR_BEAR_FUR
    );

    private static final Identifier RANGED_HASTE_ID = Identifier.of(EntityAttributes_RangedWeapon.HASTE.id.toString());
    private static final Identifier RANGED_DAMAGE_ID = Identifier.of(EntityAttributes_RangedWeapon.DAMAGE.id.toString());
    private static final Identifier ARMOR_TOUGHNESS_ID = Identifier.ofVanilla("generic.armor_toughness");

    public static final float tundra_ranged_damage_t1 = 0.065F;
    public static final float tundra_frost_spell_t1 = 1.0F;
    public static final float war_archer_damage_t1 = 0.10F;
    public static final float deadeye_damage_t1 = 0.065F;
    public static final float deadeye_haste_t1 = 0.045F;
    public static final float tundra_ranged_damage_t2 = 0.08F;
    public static final float tundra_frost_spell_t2 = 1.0F;
    public static final float tundra_haste_t2 = 0.03F;
    public static final float war_archer_damage_t2 = 0.12F;
    public static final float war_archer_armor_toughness_t2 = 1.0F;
    public static final float deadeye_damage_t2 = 0.08F;
    public static final float deadeye_haste_t2 = 0.065F;

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

    public static final ArrayList<Armor.Entry> entries = new ArrayList<>();
    private static Armor.Entry create(RegistryEntry<ArmorMaterial> material, Identifier id, int durability, Armor.Set.ItemFactory factory, ItemConfig.ArmorSet defaults) {
        var entry = Armor.Entry.create(
                material,
                id,
                durability,
                factory,
                defaults);
        entries.add(entry);
        return entry;
    }

    public static final Armor.Set tundra_hunter_t1 =
            create(
                    material_tundra_hunter,
                    Identifier.of(MOD_ID, "tundra_hunter"),
                            25,
                    TundraArcherArmor::new,
                    ItemConfig.ArmorSet.with(
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.FROST.id, tundra_frost_spell_t1),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t1)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.FROST.id, tundra_frost_spell_t1),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t1)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.FROST.id, tundra_frost_spell_t1),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t1)
                                    )),
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.FROST.id, tundra_frost_spell_t1),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t1)
                                    ))
                    )
            ).armorSet();

    public static final Armor.Set war_archer_t1 =
            create(
                    material_war_archer,
                    Identifier.of(MOD_ID, "war_archer"),
                    25,
                    WarArcherArmor::new,
                    ItemConfig.ArmorSet.with(
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,war_archer_damage_t1)
                                    )),
                            new ItemConfig.ArmorSet.Piece(5)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,war_archer_damage_t1)
                                    )),
                            new ItemConfig.ArmorSet.Piece(4)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,war_archer_damage_t1)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,war_archer_damage_t1)
                                    ))
                    )
            ).armorSet();

    public static final Armor.Set deadeye_t1 =
            create(
                    material_deadeye,
                    Identifier.of(MOD_ID, "deadeye"),
                    25,
                    DeadeyeArmor::new,
                    ItemConfig.ArmorSet.with(
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,deadeye_haste_t1),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,deadeye_damage_t1)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,deadeye_haste_t1),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,deadeye_damage_t1)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,deadeye_haste_t1),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,deadeye_damage_t1)
                                    )),
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,deadeye_haste_t1),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,deadeye_damage_t1)
                                    ))
                    )
            ).armorSet();

    public static final Armor.Set netherite_tundra_hunter =
            create(
                    material_netherite_tundra_hunter,
                    Identifier.of(MOD_ID, "netherite_tundra_hunter"),
                    35,
                    TundraArcherArmor::new,
                    ItemConfig.ArmorSet.with(
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.FROST.id, tundra_frost_spell_t2),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t2),
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,tundra_haste_t2)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.FROST.id, tundra_frost_spell_t2),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t2),
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,tundra_haste_t2)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.FROST.id, tundra_frost_spell_t2),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t2),
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,tundra_haste_t2)
                                    )),
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.bonus(SpellSchools.FROST.id, tundra_frost_spell_t2),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,tundra_ranged_damage_t2),
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,tundra_haste_t2)
                                    ))
                    )
            ).armorSet();

    public static final Armor.Set netherite_war_archer =
            create(
                    material_netherite_war_archer,
                    Identifier.of(MOD_ID, "netherite_war_archer"),
                    35,
                    WarArcherArmor::new,
                    ItemConfig.ArmorSet.with(
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            ItemConfig.Attribute.bonus(ARMOR_TOUGHNESS_ID,war_archer_armor_toughness_t2)
                                    )),
                            new ItemConfig.ArmorSet.Piece(5)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            ItemConfig.Attribute.bonus(ARMOR_TOUGHNESS_ID,war_archer_armor_toughness_t2)
                                    )),
                            new ItemConfig.ArmorSet.Piece(4)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            ItemConfig.Attribute.bonus(ARMOR_TOUGHNESS_ID,war_archer_armor_toughness_t2)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,war_archer_damage_t2),
                                            ItemConfig.Attribute.bonus(ARMOR_TOUGHNESS_ID,war_archer_armor_toughness_t2)
                                    ))
                    )
            ).armorSet();

    public static final Armor.Set netherite_deadeye =
            create(
                    material_netherite_deadeye,
                    Identifier.of(MOD_ID, "netherite_deadeye"),
                    35,
                    DeadeyeArmor::new,
                    ItemConfig.ArmorSet.with(
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,deadeye_damage_t2)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,deadeye_damage_t2)
                                    )),
                            new ItemConfig.ArmorSet.Piece(3)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,deadeye_damage_t2)
                                    )),
                            new ItemConfig.ArmorSet.Piece(2)
                                    .addAll(List.of(
                                            ItemConfig.Attribute.multiply(RANGED_HASTE_ID,deadeye_haste_t2),
                                            ItemConfig.Attribute.multiply(RANGED_DAMAGE_ID,deadeye_damage_t2)
                                    ))
                    )
            ).armorSet();

    public static void register(Map<String, ItemConfig.ArmorSet> configs) {
        Armor.register(configs, entries, Group.KEY);
    }

}

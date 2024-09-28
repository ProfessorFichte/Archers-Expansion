package com.archers_expansion.items.armors;

import com.archers_expansion.items.Group;
import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.recipe.Ingredient;
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
    public static final ArrayList<Armor.Entry> entries = new ArrayList<>();
    private static Armor.Entry create(Armor.CustomMaterial material, ItemConfig.ArmorSet defaults) {
        return new Armor.Entry(material, null, defaults);
    }

    private static final Supplier<Ingredient> TUNDRA_INGREDIENTS = () -> Ingredient.ofItems(
            MRPGCItems.POLAR_BEAR_FUR
    );

    private static final Identifier RANGED_HASTE_ID = new Identifier(EntityAttributes_RangedWeapon.HASTE.id.toString());
    private static final Identifier RANGED_DAMAGE_ID = new Identifier(EntityAttributes_RangedWeapon.DAMAGE.id.toString());

    public static final float tundra_ranged_damage_t1 = 0.065F;
    public static final float tundra_frost_spell_t1 = 1.0F;
    public static final float war_archer_damage_t1 = 0.10F;
    public static final float deadeye_damage_t1 = 0.065F;
    public static final float deadeye_haste_t1 = 0.045F;

    public static final Armor.Set tundra_hunter_t1 =
            create(
                    new Armor.CustomMaterial(
                            "tundra_hunter",
                            20,
                            10,
                            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                            TUNDRA_INGREDIENTS
                    ),
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
            )
                    .bundle(material -> new Armor.Set(MOD_ID,
                            new TundraArcherArmor(material, ArmorItem.Type.HELMET, new Item.Settings()),
                            new TundraArcherArmor(material, ArmorItem.Type.CHESTPLATE, new Item.Settings()),
                            new TundraArcherArmor(material, ArmorItem.Type.LEGGINGS, new Item.Settings()),
                            new TundraArcherArmor(material, ArmorItem.Type.BOOTS, new Item.Settings())
                    ))
                    .put(entries)
                    .armorSet();

    public static final Armor.Set war_archer_t1 =
            create(
                    new Armor.CustomMaterial(
                            "war_archer",
                            20,
                            10,
                            SoundEvents.ITEM_ARMOR_EQUIP_CHAIN,
                            () -> { return Ingredient.ofItems(Items.IRON_INGOT); }
                    ),
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
            )
                    .bundle(material -> new Armor.Set(MOD_ID,
                            new WarArcherArmor(material, ArmorItem.Type.HELMET, new Item.Settings()),
                            new WarArcherArmor(material, ArmorItem.Type.CHESTPLATE, new Item.Settings()),
                            new WarArcherArmor(material, ArmorItem.Type.LEGGINGS, new Item.Settings()),
                            new WarArcherArmor(material, ArmorItem.Type.BOOTS, new Item.Settings())
                    ))
                    .put(entries)
                    .armorSet();

    public static final Armor.Set deadeye_t1 =
            create(
                    new Armor.CustomMaterial(
                            "deadeye",
                            20,
                            10,
                            SoundEvents.ITEM_ARMOR_EQUIP_LEATHER,
                            () -> { return Ingredient.ofItems(Items.LEATHER); }
                    ),
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
            )
                    .bundle(material -> new Armor.Set(MOD_ID,
                            new DeadeyeArmor(material, ArmorItem.Type.HELMET, new Item.Settings()),
                            new DeadeyeArmor(material, ArmorItem.Type.CHESTPLATE, new Item.Settings()),
                            new DeadeyeArmor(material, ArmorItem.Type.LEGGINGS, new Item.Settings()),
                            new DeadeyeArmor(material, ArmorItem.Type.BOOTS, new Item.Settings())
                    ))
                    .put(entries)
                    .armorSet();

    public static void register(Map<String, ItemConfig.ArmorSet> configs) {
        Armor.register(configs, entries, Group.KEY);
    }

}

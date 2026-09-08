package com.archers_expansion.items;

import net.minecraft.item.ArmorMaterial;
import net.spell_engine.rpg_series.item.Armor;

public class ArchersExpansionArmor extends Armor.CustomItem {
    public ArchersExpansionArmor(ArmorMaterial material, Type slot, Settings settings) {
        super(material, slot, settings);
    }

    public static ArchersExpansionArmor tundra_hunter(ArmorMaterial material, Type slot, Settings settings) {
        var armor = new ArchersExpansionArmor(material, slot, settings);
        return armor;
    }
    public static ArchersExpansionArmor deadeye(ArmorMaterial material, Type slot, Settings settings) {
        var armor = new ArchersExpansionArmor(material, slot, settings);
        return armor;
    }
    public static ArchersExpansionArmor war_archer(ArmorMaterial material, Type slot, Settings settings) {
        var armor = new ArchersExpansionArmor(material, slot, settings);
        return armor;
    }
}
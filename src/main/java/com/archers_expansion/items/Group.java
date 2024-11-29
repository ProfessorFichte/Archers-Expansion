package com.archers_expansion.items;

import com.archers_expansion.ArchersExpansionMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class Group {
    public static Identifier ID = Identifier.of(MOD_ID, "generic");
    public static RegistryKey<ItemGroup> KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(),Identifier.of(MOD_ID,"generic"));
    public static ItemGroup ARCHERS_EXPANSION;

    public static void registerItemGroups() {
        ArchersExpansionMod.LOGGER.info("Registering Item Groups for " + MOD_ID);
    }
}

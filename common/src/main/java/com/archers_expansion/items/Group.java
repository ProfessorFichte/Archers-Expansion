package com.archers_expansion.items;

import com.archers_expansion.ArchersExpansionMod;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class Group {
    public static Identifier ID = new Identifier(MOD_ID, "generic");
    public static RegistryKey<ItemGroup> KEY = RegistryKey.of(Registries.ITEM_GROUP.getKey(), ID);
    public static ItemGroup ARCHERS_EXPANSION;

    public static ItemStack icon() {
        return new ItemStack(Armors.war_archer_t1.armorSet().head.asItem());
    }

    public static Text displayName() {
        return Text.translatable("itemGroup." + MOD_ID + ".general");
    }

    /// Loader-neutral on this line: vanilla `ItemGroup.Builder` replaces `FabricItemGroup.builder()`
    /// (Fabric API) and NeoForge's `ItemGroup.builder()` (a NeoForge addition that Forge 47 lacks).
    /// `ITEM_GROUP` is a plain vanilla registry, unfrozen for the whole Forge `RegisterEvent` phase,
    /// so this can run from inside the `ITEM` window. Row/column are irrelevant for a separate group.
    public static void registerItemGroups() {
        ArchersExpansionMod.LOGGER.info("Registering Item Groups for " + MOD_ID);
        ARCHERS_EXPANSION = new ItemGroup.Builder(ItemGroup.Row.TOP, 0)
                .icon(Group::icon)
                .displayName(displayName())
                .build();
        Registry.register(Registries.ITEM_GROUP, KEY, ARCHERS_EXPANSION);
    }
}

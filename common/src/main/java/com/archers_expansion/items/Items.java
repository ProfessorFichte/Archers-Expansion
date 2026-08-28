package com.archers_expansion.items;

import com.archers_expansion.ArchersExpansionMod;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

import java.util.HashMap;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class Items {

    public static final HashMap<String, Item> entries;

    static {
        entries = new HashMap<>();
        for(var entry: Armors.entries) {
            var set = entry.armorSet();
            for (var piece: set.pieces()) {
                var armorItem = (ArmorItem) piece;
                entries.put(set.idOf(armorItem).toString(), armorItem);
            }
        }
    }

    public static void registerModItems(){
        ArchersExpansionMod.LOGGER.info("Registering Mod Items for " + MOD_ID);
    }
}

package com.archers_expansion.items;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.items.armors.Armors;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.trinket.SpellBooks;
import net.spell_engine.api.spell.SpellContainer;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;

import java.util.HashMap;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;
import static com.archers_expansion.items.Group.KEY;

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
        SpellBooks.createAndRegister(Identifier.of(MOD_ID,"tundra_hunter"), SpellContainer.ContentType.ARCHERY,KEY);
        SpellBooks.createAndRegister(Identifier.of(MOD_ID,"war_archer"), SpellContainer.ContentType.ARCHERY,KEY);
        SpellBooks.createAndRegister(Identifier.of(MOD_ID,"deadeye"), SpellContainer.ContentType.ARCHERY,KEY);

        ItemGroupEvents.modifyEntriesEvent(Group.KEY).register((content) -> {
        });
        ArchersExpansionMod.LOGGER.info("Registering Mod Items for " + MOD_ID);
    }
}

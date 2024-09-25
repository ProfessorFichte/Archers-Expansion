package com.archers_expansion.items;

import com.archers_expansion.ArchersExpansionMod;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.trinket.SpellBooks;
import net.spell_engine.api.spell.SpellContainer;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;
import static com.archers_expansion.items.Group.KEY;

public class Items {

    public static void registerModItems(){
        SpellBooks.createAndRegister(new Identifier(MOD_ID,"tundra_hunter"), SpellContainer.ContentType.ARCHERY,KEY);
        SpellBooks.createAndRegister(new Identifier(MOD_ID,"war_archer"), SpellContainer.ContentType.ARCHERY,KEY);
        SpellBooks.createAndRegister(new Identifier(MOD_ID,"deadeye"), SpellContainer.ContentType.ARCHERY,KEY);

        ItemGroupEvents.modifyEntriesEvent(Group.KEY).register((content) -> {
        });
        ArchersExpansionMod.LOGGER.info("Registering Mod Items for " + MOD_ID);
    }
}

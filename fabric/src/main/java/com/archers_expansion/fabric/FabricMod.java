package com.archers_expansion .fabric;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.entity.ModEntitiesRegistry;
import com.archers_expansion.items.Armors;
import com.archers_expansion.items.Group;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import net.minecraft.item.ArmorItem;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ArchersExpansionMod.init();
        ArchersExpansionMod.registerEffects();
        ArchersExpansionMod.registerSounds();
        ArchersExpansionMod.registerEntities();
        ArchersExpansionMod.registerItems();

        ModEntitiesRegistry.registerEntityAttributes(FabricDefaultAttributeRegistry::register);

        Armors.forEachGroupOverride((pieces, key) -> {
            ItemGroupEvents.modifyEntriesEvent(Group.KEY).register(content -> {
                content.getDisplayStacks().removeIf(stack -> pieces.stream().anyMatch(p -> stack.isOf((ArmorItem) p)));
                content.getSearchTabStacks().removeIf(stack -> pieces.stream().anyMatch(p -> stack.isOf((ArmorItem) p)));
            });
            ItemGroupEvents.modifyEntriesEvent(key).register(content -> {
                for (var piece : pieces) {
                    content.add((ArmorItem) piece);
                }
            });
        });

    }
}

package com.archers_expansion .fabric;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.entity.ModEntitiesRegistry;
import com.archers_expansion.items.Armors;
import com.archers_expansion.items.Group;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

import net.minecraft.item.ArmorItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ArchersExpansionMod.init();
        ArchersExpansionMod.registerEffects();
        ArchersExpansionMod.registerSounds();
        ArchersExpansionMod.registerEntities();
        registerItemGroup();
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

    private void registerItemGroup() {
        Group.ARCHERS_EXPANSION = FabricItemGroup.builder()
                .icon(Group::icon)
                .displayName(Group.displayName())
                .build();
        Registry.register(Registries.ITEM_GROUP, Group.KEY, Group.ARCHERS_EXPANSION);
    }
}

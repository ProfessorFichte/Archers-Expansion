package com.archers_expansion .neoforge;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.entity.ModEntitiesRegistry;
import com.archers_expansion.items.Armors;
import com.archers_expansion.items.Group;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKeys;

import java.util.ArrayList;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.entity.EntityAttributeCreationEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(ArchersExpansionMod.MOD_ID)
public final class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
        ArchersExpansionMod.init();
        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
        modBus.addListener(EntityAttributeCreationEvent.class, NeoForgeMod::registerAttributes);
        modBus.addListener(BuildCreativeModeTabContentsEvent.class, NeoForgeMod::onBuildCreativeTabContents);
    }

    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.ITEM_GROUP, reg -> {
            Group.ARCHERS_EXPANSION = ItemGroup.builder()
                    .icon(Group::icon)
                    .displayName(Group.displayName())
                    .build();
            Registry.register(Registries.ITEM_GROUP, Group.KEY, Group.ARCHERS_EXPANSION);
        });
        event.register(RegistryKeys.SOUND_EVENT, reg -> {
            ArchersExpansionMod.registerSounds();
        });
        event.register(RegistryKeys.STATUS_EFFECT, reg -> {
            ArchersExpansionMod.registerEffects();
        });
        event.register(RegistryKeys.ENTITY_TYPE, reg -> {
            ArchersExpansionMod.registerEntities();
        });
        event.register(RegistryKeys.ITEM, reg -> {
            ArchersExpansionMod.registerItems();
        });
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        ModEntitiesRegistry.registerEntityAttributes((entityType, builder) -> event.put(entityType, builder.build()));
    }

    public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        Armors.forEachGroupOverride((pieces, key) -> {
            if (event.getTabKey().equals(Group.KEY)) {
                for (var piece : pieces) {
                    removeFromTab(event, (ArmorItem) piece);
                }
            } else if (event.getTabKey().equals(key)) {
                for (var piece : pieces) {
                    event.add((ArmorItem) piece);
                }
            }
        });
    }

    private static void removeFromTab(BuildCreativeModeTabContentsEvent event, Item item) {
        var toRemove = new ArrayList<ItemStack>();
        for (var stack : event.getParentEntries()) {
            if (stack.isOf(item)) {
                toRemove.add(stack);
            }
        }
        for (var stack : event.getSearchEntries()) {
            if (stack.isOf(item)) {
                toRemove.add(stack);
            }
        }
        for (var stack : toRemove) {
            event.remove(stack, ItemGroup.StackVisibility.PARENT_AND_SEARCH_TABS);
        }
    }
}

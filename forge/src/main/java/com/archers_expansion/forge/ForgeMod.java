package com.archers_expansion.forge;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.entity.ModEntitiesRegistry;
import com.archers_expansion.forge.client.ForgeClient;
import com.archers_expansion.items.Armors;
import com.archers_expansion.items.Group;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.RegistryKeys;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.registries.RegisterEvent;

import java.util.ArrayList;

/// Forge 47 entrypoint (1.20.1 port of the NeoForge entrypoint).
///
/// Forge locks every vanilla registry outside its own `RegisterEvent` window, so each `registerX()`
/// call sits inside the window of the registry it writes to. The creative tab is created inside the
/// `ITEM` window: `ITEM_GROUP` is a plain vanilla registry (not Forge-wrapped) and stays unfrozen for
/// the whole `RegisterEvent` phase.
///
/// The Polar Bear's summon attributes need no listener here: `ModEntitiesRegistry.registerEntities()`
/// hands them to SpellEngine's `Platform.util().registerSummonedEntityAttributes`, which buffers them
/// until SpellEngine's own `EntityAttributeCreationEvent` listener flushes them — that event fires
/// after the `ENTITY_TYPE` window below, so the ordering holds. The Alter Ego is a plain
/// `PathAwareEntity`, so it does need the listener.
@Mod(ArchersExpansionMod.MOD_ID)
public final class ForgeMod {
    // FMLJavaModLoadingContext.get() is flagged for removal by late 47.x builds, but the
    // constructor-injected replacement doesn't exist on early 47.x; get() works on all of [47,).
    @SuppressWarnings("removal")
    public ForgeMod() {
        ArchersExpansionMod.init();

        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Explicit event classes: Forge 47's plain addListener(Consumer) infers the event type from the
        // lambda via TypeTools, which is fragile; the 4-arg overload takes it directly.
        modBus.addListener(EventPriority.NORMAL, false, RegisterEvent.class, ForgeMod::register);
        modBus.addListener(EventPriority.NORMAL, false, EntityAttributeCreationEvent.class,
                ForgeMod::registerAttributes);
        modBus.addListener(EventPriority.NORMAL, false, BuildCreativeModeTabContentsEvent.class,
                ForgeMod::onBuildCreativeTabContents);

        if (FMLEnvironment.dist == Dist.CLIENT) {
            modBus.addListener(EventPriority.NORMAL, false, FMLClientSetupEvent.class,
                    ForgeClient::onClientSetup);
            ForgeClient.register(modBus);
        }
    }

    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.SOUND_EVENT, reg -> ArchersExpansionMod.registerSounds());
        event.register(RegistryKeys.STATUS_EFFECT, reg -> ArchersExpansionMod.registerEffects());
        event.register(RegistryKeys.ENTITY_TYPE, reg -> ArchersExpansionMod.registerEntities());
        event.register(RegistryKeys.ITEM, reg -> ArchersExpansionMod.registerItems());
    }

    public static void registerAttributes(EntityAttributeCreationEvent event) {
        ModEntitiesRegistry.registerEntityAttributes((entityType, builder) -> event.put(entityType, builder.build()));
    }

    /// Armory-compat sets are registered into SpellEngine's own `archers_expansion:generic` listener
    /// like every other set, so they have to be pulled back out of it and pushed into the Armory tab.
    /// Forge 47's event carries a single `MutableHashedLinkedMap` of entries (NeoForge's split
    /// `getParentEntries()` / `getSearchEntries()` + `remove(stack, visibility)` do not exist here).
    public static void onBuildCreativeTabContents(BuildCreativeModeTabContentsEvent event) {
        Armors.forEachGroupOverride((pieces, key) -> {
            if (event.getTabKey().equals(Group.KEY)) {
                for (var piece : pieces) {
                    removeFromTab(event, (ArmorItem) piece);
                }
            } else if (event.getTabKey().equals(key)) {
                for (var piece : pieces) {
                    var item = (ArmorItem) piece;
                    event.accept(() -> item);
                }
            }
        });
    }

    private static void removeFromTab(BuildCreativeModeTabContentsEvent event, Item item) {
        var toRemove = new ArrayList<ItemStack>();
        for (var entry : event.getEntries()) {
            if (entry.getKey().isOf(item)) {
                toRemove.add(entry.getKey());
            }
        }
        for (var stack : toRemove) {
            event.getEntries().remove(stack);
        }
    }
}

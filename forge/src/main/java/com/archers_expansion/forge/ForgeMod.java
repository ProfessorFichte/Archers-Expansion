package com.archers_expansion.forge;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.effect.ArchersExpansionEffects;
import com.archers_expansion.entity.ModEntitiesRegistry;
import com.archers_expansion.forge.client.ForgeClient;
import com.archers_expansion.items.Armors;
import com.archers_expansion.items.Group;
import com.archers_expansion.items.Items;
import com.archers_expansion.sounds.Sounds;
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
import net.spell_engine.api.effect.Effects;

import java.util.ArrayList;

/// Forge 47 entrypoint (1.20.1 port of the NeoForge entrypoint).
///
/// Forge only clears the vanilla `NamespacedWrapper`'s own lock from **47.4.0** onwards, so on Forge
/// 47.0-47.3 (and NeoForge 1.20.1) a plain `Registry.register` throws "Can not register to a locked
/// registry" even inside the correct `RegisterEvent` window - and `mods.toml` declares
/// `loaderVersion = "[47,)"`, so those are supported configurations. Everything below therefore writes
/// through the `RegisterHelper` that `RegisterEvent` hands out.
///
/// The loops duplicate what `common` runs on Fabric, on purpose: the workaround stays inside `forge/`
/// and the Fabric path is untouched. Each block is declared unconditionally - Forge posts one event per
/// registry and `event.register` is a no-op unless its key matches.
///
/// The Polar Bear's summon attributes need no listener here: `ModEntitiesRegistry.registerSummonAttributes()`
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
        // No link step: `Sounds.Entry` holds the raw `SoundEvent`, never a `RegistryEntry`.
        event.register(RegistryKeys.SOUND_EVENT, helper ->
                Sounds.soundsToRegister().forEach(helper::register));

        event.register(RegistryKeys.STATUS_EFFECT, helper -> {
            ArchersExpansionEffects.effectsToRegister(ArchersExpansionMod.effectsConfig.value)
                    .forEach(helper::register);
            Effects.linkEntries(ArchersExpansionEffects.entries);
            // Reads `Entry#effect`, so it has to follow the loop, exactly as on the vanilla path.
            ArchersExpansionEffects.installBehaviours();
            // The trailing side effect of `ArchersExpansionMod.registerEffects()`.
            ArchersExpansionMod.effectsConfig.save();
        });

        event.register(RegistryKeys.ENTITY_TYPE, helper -> {
            ModEntitiesRegistry.entityTypesToRegister().forEach(helper::register);
            ModEntitiesRegistry.registerSummonAttributes();
        });

        event.register(RegistryKeys.ITEM, helper -> {
            // Class-init trigger for `Items.entries`, which mirrors the base armor sets.
            Items.registerModItems();
            // NOT `Armor.itemsToRegister(...)`: the Armory-compat sets are appended to `Armors.entries`
            // only by this method, and calling the Spell Engine helper directly would drop all twelve
            // pieces with no error.
            Armors.itemsToRegister(ArchersExpansionMod.itemConfig.value.armor_sets).forEach(helper::register);
            // The trailing side effect of `ArchersExpansionMod.registerItems()`.
            ArchersExpansionMod.itemConfig.save();
        });

        // `creative_mode_tab` is `RegisterEvent` 65 while `item` is 7 - registering the group from the
        // ITEM pass writes into a registry whose event has not fired yet.
        event.register(RegistryKeys.ITEM_GROUP, helper -> {
            Group.createItemGroup();
            helper.register(Group.ID, Group.ARCHERS_EXPANSION);
        });
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

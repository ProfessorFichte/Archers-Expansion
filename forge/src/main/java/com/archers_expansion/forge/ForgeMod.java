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

@Mod(ArchersExpansionMod.MOD_ID)
public final class ForgeMod {
    @SuppressWarnings("removal")
    public ForgeMod() {
        ArchersExpansionMod.init();

        var modBus = FMLJavaModLoadingContext.get().getModEventBus();
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

    // Goes through the helper on purpose, on Forge 47.0-47.3 a plain Registry.register throws "Can not register to a locked registry".
    public static void register(RegisterEvent event) {
        event.register(RegistryKeys.SOUND_EVENT, helper ->
                Sounds.soundsToRegister().forEach(helper::register));

        event.register(RegistryKeys.STATUS_EFFECT, helper -> {
            ArchersExpansionEffects.effectsToRegister(ArchersExpansionMod.effectsConfig.value)
                    .forEach(helper::register);
            Effects.linkEntries(ArchersExpansionEffects.entries);
            ArchersExpansionEffects.installBehaviours();
            ArchersExpansionMod.effectsConfig.save();
        });

        event.register(RegistryKeys.ENTITY_TYPE, helper -> {
            ModEntitiesRegistry.entityTypesToRegister().forEach(helper::register);
            ModEntitiesRegistry.registerSummonAttributes();
        });

        event.register(RegistryKeys.ITEM, helper -> {
            Items.registerModItems();
            Armors.itemsToRegister(ArchersExpansionMod.itemConfig.value.armor_sets).forEach(helper::register);
            ArchersExpansionMod.itemConfig.save();
        });

        event.register(RegistryKeys.ITEM_GROUP, helper -> {
            Group.createItemGroup();
            helper.register(Group.ID, Group.ARCHERS_EXPANSION);
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

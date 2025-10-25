package com.archers_expansion .neoforge;

import com.archers_expansion.ArchersExpansionMod;
import net.minecraft.registry.RegistryKeys;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.registries.RegisterEvent;

@Mod(ArchersExpansionMod.MOD_ID)
public final class NeoForgeMod {
    public NeoForgeMod(IEventBus modBus) {
        ArchersExpansionMod.init();
        modBus.addListener(RegisterEvent.class, NeoForgeMod::register);
    }

    public static void register(RegisterEvent event) {
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
}

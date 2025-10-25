package com.archers_expansion .fabric;

import com.archers_expansion.ArchersExpansionMod;
import net.fabricmc.api.ModInitializer;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ArchersExpansionMod.init();
        ArchersExpansionMod.registerEffects();
        ArchersExpansionMod.registerSounds();
        ArchersExpansionMod.registerEntities();
        ArchersExpansionMod.registerItems();
    }
}

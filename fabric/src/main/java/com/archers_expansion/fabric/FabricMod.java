package com.archers_expansion .fabric;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.entity.ModEntitiesRegistry;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;

public final class FabricMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ArchersExpansionMod.init();
        ArchersExpansionMod.registerEffects();
        ArchersExpansionMod.registerSounds();
        ArchersExpansionMod.registerEntities();
        ArchersExpansionMod.registerItems();

        ModEntitiesRegistry.registerEntityAttributes(FabricDefaultAttributeRegistry::register);
    }
}

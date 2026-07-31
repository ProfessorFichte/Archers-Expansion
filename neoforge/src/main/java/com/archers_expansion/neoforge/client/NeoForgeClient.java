package com.archers_expansion.neoforge.client;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.client.ArchersExpansionModClient;
import com.archers_expansion.client.entity.GlacialBearEntityModel;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;

@EventBusSubscriber(modid = ArchersExpansionMod.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClient {
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GlacialBearEntityModel.LAYER_LOCATION, GlacialBearEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ArchersExpansionModClient.init();
    }
}

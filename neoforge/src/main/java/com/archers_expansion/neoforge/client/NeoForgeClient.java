package com.archers_expansion.neoforge.client;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.client.ArchersExpansionModClient;
import com.archers_expansion.client.entity.AlterEgoRenderer;
import com.archers_expansion.client.entity.ExplosiveBarrelRenderer;
import com.archers_expansion.client.entity.GlacialBearEntityModel;
import com.archers_expansion.client.entity.PoisonFlaskRenderer;
import com.archers_expansion.client.entity.SpellPolarBearRenderer;
import com.archers_expansion.entity.AlterEgoEntity;
import com.archers_expansion.entity.ExplosiveBarrelEntity;
import com.archers_expansion.entity.FrozenFussiladeEntity;
import com.archers_expansion.entity.PoisonFlaskProjectile;
import com.archers_expansion.entity.PolarBearEntity;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;
import net.neoforged.neoforge.client.event.EntityRenderersEvent;
import net.spell_engine.client.render.SpellCloudRenderer;

@EventBusSubscriber(modid = ArchersExpansionMod.MOD_ID, value = Dist.CLIENT)
public class NeoForgeClient {
    @SubscribeEvent
    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GlacialBearEntityModel.LAYER_LOCATION, GlacialBearEntityModel::createBodyLayer);
    }

    @SubscribeEvent
    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ExplosiveBarrelEntity.ENTITY_TYPE, ExplosiveBarrelRenderer::new);
        event.registerEntityRenderer(AlterEgoEntity.ENTITY_TYPE, AlterEgoRenderer::new);
        event.registerEntityRenderer(PolarBearEntity.ENTITY_TYPE, SpellPolarBearRenderer::new);
        event.registerEntityRenderer(PoisonFlaskProjectile.ENTITY_TYPE, PoisonFlaskRenderer::new);
        event.registerEntityRenderer(FrozenFussiladeEntity.ENTITY_TYPE, SpellCloudRenderer::new);
    }

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        ArchersExpansionModClient.init();
    }
}

package com.archers_expansion.forge.client;

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
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.spell_engine.client.render.SpellCloudRenderer;

public class ForgeClient {
    public static void register(IEventBus modBus) {
        modBus.addListener(EventPriority.NORMAL, false, EntityRenderersEvent.RegisterLayerDefinitions.class,
                ForgeClient::onRegisterLayerDefinitions);
        modBus.addListener(EventPriority.NORMAL, false, EntityRenderersEvent.RegisterRenderers.class,
                ForgeClient::onRegisterRenderers);
    }

    public static void onRegisterLayerDefinitions(EntityRenderersEvent.RegisterLayerDefinitions event) {
        event.registerLayerDefinition(GlacialBearEntityModel.LAYER_LOCATION, GlacialBearEntityModel::createBodyLayer);
    }

    public static void onRegisterRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerEntityRenderer(ExplosiveBarrelEntity.ENTITY_TYPE, ExplosiveBarrelRenderer::new);
        event.registerEntityRenderer(AlterEgoEntity.ENTITY_TYPE, AlterEgoRenderer::new);
        event.registerEntityRenderer(PolarBearEntity.ENTITY_TYPE, SpellPolarBearRenderer::new);
        event.registerEntityRenderer(PoisonFlaskProjectile.ENTITY_TYPE, PoisonFlaskRenderer::new);
        event.registerEntityRenderer(FrozenFussiladeEntity.ENTITY_TYPE, SpellCloudRenderer::new);
    }

    public static void onClientSetup(FMLClientSetupEvent event) {
        ArchersExpansionModClient.init();
    }
}

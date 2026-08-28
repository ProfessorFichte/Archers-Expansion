package com.archers_expansion .fabric.client;

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
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.spell_engine.client.render.SpellCloudRenderer;

public final class FabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ArchersExpansionModClient.init();

        EntityModelLayerRegistry.registerModelLayer(GlacialBearEntityModel.LAYER_LOCATION, GlacialBearEntityModel::createBodyLayer);

        EntityRendererRegistry.register(ExplosiveBarrelEntity.ENTITY_TYPE, ExplosiveBarrelRenderer::new);
        EntityRendererRegistry.register(AlterEgoEntity.ENTITY_TYPE, AlterEgoRenderer::new);
        EntityRendererRegistry.register(PolarBearEntity.ENTITY_TYPE, SpellPolarBearRenderer::new);
        EntityRendererRegistry.register(PoisonFlaskProjectile.ENTITY_TYPE, PoisonFlaskRenderer::new);
        EntityRendererRegistry.register(FrozenFussiladeEntity.ENTITY_TYPE, SpellCloudRenderer::new);
    }
}

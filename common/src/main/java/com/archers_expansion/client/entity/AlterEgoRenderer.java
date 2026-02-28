package com.archers_expansion.client.entity;

import com.archers_expansion.entity.AlterEgoEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.feature.HeldItemFeatureRenderer;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.DefaultSkinHelper;
import net.minecraft.util.Identifier;

/**
 * Renderer for AlterEgoEntity that mimics a player's appearance.
 * Uses a basic biped model with the player's skin texture, armor, and held items.
 */
public class AlterEgoRenderer extends MobEntityRenderer<AlterEgoEntity, BipedEntityModel<AlterEgoEntity>> {

    public AlterEgoRenderer(EntityRendererFactory.Context context) {
        super(context, new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER)), 0.5F);

        // Add armor rendering
        this.addFeature(new ArmorFeatureRenderer<>(this,
            new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER_INNER_ARMOR)),
            new BipedEntityModel<>(context.getPart(EntityModelLayers.PLAYER_OUTER_ARMOR)),
            context.getModelManager()));

        // Add held item rendering
        this.addFeature(new HeldItemFeatureRenderer<>(this, context.getHeldItemRenderer()));
    }

    @Override
    public Identifier getTexture(AlterEgoEntity entity) {
        var playerUuid = entity.getPlayerUuid();
        if (playerUuid != null) {
            var minecraft = MinecraftClient.getInstance();
            var connection = minecraft.getNetworkHandler();
            if (connection != null) {
                var playerEntry = connection.getPlayerListEntry(playerUuid);
                if (playerEntry != null) {
                    return playerEntry.getSkinTextures().texture();
                }
            }
        }

        // Default Steve skin Fallback
        return DefaultSkinHelper.getSkinTextures(playerUuid != null ? playerUuid : new java.util.UUID(0, 0)).texture();
    }
}

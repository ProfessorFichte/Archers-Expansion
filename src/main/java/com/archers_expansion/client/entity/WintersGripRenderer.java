package com.archers_expansion.client.entity;

import com.archers_expansion.entity.WintersGripEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.RotationAxis;
import net.spell_engine.api.render.CustomLayers;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.api.render.LightEmission;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class WintersGripRenderer <T extends WintersGripEntity> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;

    public WintersGripRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public Identifier getTexture(T entity) {
        return null;
    }

    public static final Identifier baseId = Identifier.of(MOD_ID, "effect/winters_grip");

    private static final RenderLayer GLOWING_RENDER_LAYER =
            CustomLayers.spellEffect(LightEmission.RADIATE, false);


    public void render(T entity, float yaw, float tickDelta, MatrixStack matrixStack, VertexConsumerProvider
            vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumers, light);
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-1F * entity.getYaw() + 180F));
        matrixStack.translate(0, 0.5F, 0);
        CustomModels.render(GLOWING_RENDER_LAYER, itemRenderer, baseId, matrixStack, vertexConsumers, light, entity.getId());
        matrixStack.translate(0.5, 0, 0.5);
        matrixStack.pop();
    }
}
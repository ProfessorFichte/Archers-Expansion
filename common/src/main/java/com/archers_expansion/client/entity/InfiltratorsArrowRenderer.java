package com.archers_expansion.client.entity;

import com.archers_expansion.client.render.NonGlowingProjectileLayer;
import com.archers_expansion.entity.InfiltratorsArrowProjectile;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import net.spell_engine.api.render.CustomModels;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class InfiltratorsArrowRenderer<T extends InfiltratorsArrowProjectile> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;

    public static final Identifier modelId = Identifier.of(MOD_ID, "spell_projectile/infiltrators_arrow");

    private static final RenderLayer RENDER_LAYER = NonGlowingProjectileLayer.nonGlowingProjectile();

    public InfiltratorsArrowRenderer(EntityRendererFactory.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();
    }

    @Override
    public Identifier getTexture(T entity) {
        return null;
    }

    @Override
    public void render(T entity, float yaw, float tickDelta, MatrixStack matrixStack,
                       VertexConsumerProvider vertexConsumers, int light) {
        super.render(entity, yaw, tickDelta, matrixStack, vertexConsumers, light);

        matrixStack.push();

        var rawVelocity = entity.getVelocity();
        if (rawVelocity.lengthSquared() > 1.0E-6) {
            var velocity = rawVelocity.normalize();
            var directionBasedYaw = Math.toDegrees(Math.atan2(velocity.x, velocity.z)) + 180.0;
            var directionBasedPitch = Math.toDegrees(Math.asin(MathHelper.clamp(velocity.y, -1.0, 1.0)));
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees((float) directionBasedYaw));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees((float) directionBasedPitch));
        } else {
            matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(-entity.getYaw()));
            matrixStack.multiply(RotationAxis.POSITIVE_X.rotationDegrees(entity.getPitch()));
        }

        CustomModels.render(
            RENDER_LAYER,
            itemRenderer,
            modelId,
            matrixStack,
            vertexConsumers,
            light,
            entity.getId()
        );

        matrixStack.pop();
    }
}

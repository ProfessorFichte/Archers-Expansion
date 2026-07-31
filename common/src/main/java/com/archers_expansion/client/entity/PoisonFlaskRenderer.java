package com.archers_expansion.client.entity;

import com.archers_expansion.entity.PoisonFlaskProjectile;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.spell_engine.api.render.CustomLayers;
import net.spell_engine.api.render.CustomModels;
import net.spell_engine.api.render.LightEmission;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class PoisonFlaskRenderer<T extends PoisonFlaskProjectile> extends EntityRenderer<T> {
    private final ItemRenderer itemRenderer;

    public static final Identifier modelId = Identifier.of(MOD_ID, "spell_projectile/venom_flask");

    private static final RenderLayer RENDER_LAYER = CustomLayers.projectile(LightEmission.GLOW);

    public PoisonFlaskRenderer(EntityRendererFactory.Context context) {
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

        var pitch = MathHelper.lerp(tickDelta, entity.prevPitch, entity.getPitch());
        var renderYaw = MathHelper.lerp(tickDelta, entity.prevYaw, entity.getYaw());
        matrixStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Y.rotationDegrees(renderYaw - 90F));
        matrixStack.multiply(net.minecraft.util.math.RotationAxis.POSITIVE_Z.rotationDegrees(pitch));
        matrixStack.scale(0.8F, 0.8F, 0.8F);

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

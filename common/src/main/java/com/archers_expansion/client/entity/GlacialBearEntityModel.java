package com.archers_expansion.client.entity;

import com.archers_expansion.entity.PolarBearEntity;
import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModelLayer;
import net.minecraft.client.render.entity.model.SinglePartEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class GlacialBearEntityModel extends SinglePartEntityModel<PolarBearEntity> {
    public static final EntityModelLayer LAYER_LOCATION = new EntityModelLayer(Identifier.of(MOD_ID, "glacial_bear"), "main");

    private final ModelPart root;
    private final ModelPart bone6;
    private final ModelPart bone5;
    private final ModelPart bodyback;
    private final ModelPart middle;
    private final ModelPart bv;
    private final ModelPart head;
    private final ModelPart leg3;
    private final ModelPart bone;
    private final ModelPart leg2;
    private final ModelPart bone2;
    private final ModelPart leg5;
    private final ModelPart bone4;
    private final ModelPart leg4;
    private final ModelPart bone3;

    public GlacialBearEntityModel(ModelPart root) {
        this.root = root;
        this.bone6 = root.getChild("bone6");
        this.bone5 = this.bone6.getChild("bone5");
        this.bodyback = this.bone5.getChild("bodyback");
        this.middle = this.bodyback.getChild("middle");
        this.bv = this.middle.getChild("bv");
        this.head = this.bv.getChild("head");
        this.leg3 = this.bone6.getChild("leg3");
        this.bone = this.leg3.getChild("bone");
        this.leg2 = this.bone6.getChild("leg2");
        this.bone2 = this.leg2.getChild("bone2");
        this.leg5 = this.bone6.getChild("leg5");
        this.bone4 = this.leg5.getChild("bone4");
        this.leg4 = this.bone6.getChild("leg4");
        this.bone3 = this.leg4.getChild("bone3");
    }

    @Override
    public ModelPart getPart() {
        return this.root;
    }

    public static TexturedModelData createBodyLayer() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();

        ModelPartData bone6 = modelPartData.addChild("bone6", ModelPartBuilder.create(), ModelTransform.pivot(-2.7713F, 4.9004F, -2.3847F));

        ModelPartData bone5 = bone6.addChild("bone5", ModelPartBuilder.create(), ModelTransform.pivot(2.7713F, 2.0996F, -14.6153F));

        ModelPartData bodyback = bone5.addChild("bodyback", ModelPartBuilder.create()
            .uv(0, 0).cuboid(-5.5F, -10.0F, -4.25F, 15.0F, 12.0F, 12.0F, new Dilation(0.0F)),
            ModelTransform.of(-2.0F, 2.0F, 29.0F, 1.5708F, 0.0F, 0.0F));

        ModelPartData crystal_r1 = bodyback.addChild("crystal_r1", ModelPartBuilder.create()
            .uv(104, 33).cuboid(-1.0F, -1.0F, -2.0F, 2.0F, 2.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(7.4769F, -4.25F, 8.5997F, -0.104F, -0.1181F, -0.2614F));

        ModelPartData crystal_r2 = bodyback.addChild("crystal_r2", ModelPartBuilder.create()
            .uv(104, 42).cuboid(-2.0F, -1.0F, -2.0F, 3.0F, 3.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(-1.3282F, -5.2874F, 8.8088F, 0.0404F, -0.0774F, -0.4815F));

        ModelPartData crystal_r3 = bodyback.addChild("crystal_r3", ModelPartBuilder.create()
            .uv(104, 105).cuboid(-0.5F, -0.5F, -2.0F, 2.0F, 2.0F, 4.0F, new Dilation(0.0F)),
            ModelTransform.of(8.0F, -5.5F, 8.75F, 0.0F, 0.3054F, 0.0F));

        ModelPartData middle = bodyback.addChild("middle", ModelPartBuilder.create()
            .uv(55, 23).cuboid(-6.5F, -5.5F, -9.5F, 13.0F, 7.0F, 11.0F, new Dilation(0.0F)),
            ModelTransform.pivot(2.0F, -11.5F, 5.25F));

        ModelPartData bv = middle.addChild("bv", ModelPartBuilder.create()
            .uv(0, 50).cuboid(-7.5F, -8.5F, -9.5F, 15.0F, 10.0F, 12.0F, new Dilation(0.0F)),
            ModelTransform.pivot(0.0F, -7.0F, 0.0F));

        ModelPartData crystal_r4 = bv.addChild("crystal_r4", ModelPartBuilder.create()
            .uv(104, 50).cuboid(-1.0F, -0.5F, -3.0F, 2.0F, 2.0F, 5.0F, new Dilation(0.0F)),
            ModelTransform.of(-1.7621F, -2.9187F, 4.4699F, -0.3815F, -0.0276F, -0.3997F));

        ModelPartData crystal_r5 = bv.addChild("crystal_r5", ModelPartBuilder.create()
            .uv(15, 108).cuboid(1.5F, -0.5F, -1.25F, 2.0F, 2.0F, 3.0F, new Dilation(0.0F)),
            ModelTransform.of(-2.7621F, -4.9187F, 4.4699F, 0.0122F, 0.4087F, -0.3946F));

        ModelPartData crystal_r6 = bv.addChild("crystal_r6", ModelPartBuilder.create()
            .uv(104, 23).cuboid(-1.5F, -1.5F, -3.0F, 3.0F, 3.0F, 6.0F, new Dilation(0.0F)),
            ModelTransform.of(-2.7621F, -4.9187F, 4.4699F, 0.0443F, -0.2423F, -0.1774F));

        ModelPartData head = bv.addChild("head", ModelPartBuilder.create()
            .uv(0, 73).cuboid(-4.5F, -5.0F, -7.0F, 9.0F, 9.0F, 8.0F, new Dilation(0.0F))
            .uv(55, 61).cuboid(-4.5F, -5.0F, -7.0F, 9.0F, 11.0F, 8.0F, new Dilation(0.5F))
            .uv(35, 73).cuboid(-2.5F, 0.0F, -10.0F, 5.0F, 3.0F, 3.0F, new Dilation(0.0F))
            .uv(104, 100).cuboid(-2.5F, 3.0F, -10.0F, 5.0F, 1.0F, 3.0F, new Dilation(0.0F))
            .uv(101, 94).cuboid(-2.5F, 4.0F, -10.0F, 5.0F, 2.0F, 3.0F, new Dilation(0.0F)),
            ModelTransform.of(0.0F, -9.5F, -3.25F, -1.5708F, 0.0F, 0.0F));

        ModelPartData head_r1 = head.addChild("head_r1", ModelPartBuilder.create()
            .uv(110, 11).mirrored().cuboid(-2.25F, -2.25F, -0.5F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)).mirrored(false),
            ModelTransform.of(-3.75F, -4.75F, -3.5F, 0.0F, 0.0F, -0.4363F));

        ModelPartData head_r2 = head.addChild("head_r2", ModelPartBuilder.create()
            .uv(110, 11).cuboid(-0.75F, -2.25F, -0.5F, 3.0F, 3.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.of(3.75F, -4.75F, -3.5F, 0.0F, 0.0F, 0.4363F));

        ModelPartData head_r3 = head.addChild("head_r3", ModelPartBuilder.create()
            .uv(110, 6).cuboid(-1.5F, -0.5F, -1.0F, 3.0F, 2.0F, 2.0F, new Dilation(-0.2F)),
            ModelTransform.of(0.0F, 0.0F, -9.5F, 0.2182F, 0.0F, 0.0F));

        ModelPartData leg3 = bone6.addChild("leg3", ModelPartBuilder.create()
            .uv(90, 61).cuboid(-2.25F, -1.25F, -2.8479F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F)),
            ModelTransform.pivot(7.0213F, 4.1975F, -7.8653F));

        ModelPartData bone = leg3.addChild("bone", ModelPartBuilder.create()
            .uv(79, 100).cuboid(-3.0F, -1.0304F, -0.7932F, 6.0F, 9.0F, 6.0F, new Dilation(0.0F))
            .uv(104, 58).cuboid(-3.0F, 6.9696F, -1.7932F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.pivot(1.25F, 6.75F, -1.902F));

        ModelPartData leg2 = bone6.addChild("leg2", ModelPartBuilder.create()
            .uv(90, 61).mirrored().cuboid(-4.75F, -1.25F, -2.8479F, 7.0F, 9.0F, 7.0F, new Dilation(0.0F)).mirrored(false),
            ModelTransform.pivot(-1.4787F, 4.1975F, -7.8653F));

        ModelPartData bone2 = leg2.addChild("bone2", ModelPartBuilder.create()
            .uv(79, 100).mirrored().cuboid(-3.0F, -1.0F, -0.45F, 6.0F, 9.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
            .uv(104, 58).mirrored().cuboid(-3.0F, 7.0F, -1.45F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false),
            ModelTransform.pivot(-1.25F, 6.75F, -1.8979F));

        ModelPartData leg5 = bone6.addChild("leg5", ModelPartBuilder.create()
            .uv(68, 81).cuboid(-2.0F, -4.0F, -4.3479F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)),
            ModelTransform.pivot(6.2713F, 4.9475F, 13.1347F));

        ModelPartData bone4 = leg5.addChild("bone4", ModelPartBuilder.create()
            .uv(54, 100).cuboid(-3.0F, -1.3182F, -1.7676F, 6.0F, 10.0F, 6.0F, new Dilation(0.0F))
            .uv(110, 3).cuboid(-3.0F, 7.6818F, -2.7676F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)),
            ModelTransform.pivot(2.0F, 5.25F, -2.098F));

        ModelPartData leg4 = bone6.addChild("leg4", ModelPartBuilder.create()
            .uv(68, 81).mirrored().cuboid(-6.0F, -4.0F, -4.3479F, 8.0F, 10.0F, 8.0F, new Dilation(0.0F)).mirrored(false),
            ModelTransform.pivot(-0.7287F, 4.9475F, 13.1347F));

        ModelPartData bone3 = leg4.addChild("bone3", ModelPartBuilder.create()
            .uv(54, 100).mirrored().cuboid(-3.0F, -1.25F, -1.25F, 6.0F, 10.0F, 6.0F, new Dilation(0.0F)).mirrored(false)
            .uv(110, 3).mirrored().cuboid(-3.0F, 7.75F, -2.25F, 6.0F, 1.0F, 1.0F, new Dilation(0.0F)).mirrored(false),
            ModelTransform.pivot(-2.0F, 5.25F, -2.098F));

        return TexturedModelData.of(modelData, 128, 128);
    }

    @Override
    public void setAngles(PolarBearEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        this.getPart().traverse().forEach(ModelPart::resetTransform);
        var locomotion = entity.isSpeedBursting() ? GlacialBearAnimations.RUN : GlacialBearAnimations.WALK;
        this.animateMovement(locomotion, limbSwing, limbSwingAmount, 2.0f, 2.5f);
        float attackSpeed = (float) entity.getAttributeValue(EntityAttributes.GENERIC_ATTACK_SPEED);
        this.updateAnimation(entity.attackAnimationState, GlacialBearAnimations.ATTACK, ageInTicks, attackSpeed);
        this.updateAnimation(entity.idleAnimationState, GlacialBearAnimations.IDLE, ageInTicks, 1.0f);
        this.updateAnimation(entity.spawnAnimationState, GlacialBearAnimations.SPAWN, ageInTicks, 1.0f);
        this.updateAnimation(entity.despawnAnimationState, GlacialBearAnimations.DEATH, ageInTicks, 1.0f);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertices, int light, int overlay, int color) {
        bone6.render(matrices, vertices, light, overlay, color);
    }
}

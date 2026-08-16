package com.archers_expansion.client.entity;

import net.minecraft.client.render.entity.animation.Animation;
import net.minecraft.client.render.entity.animation.AnimationHelper;
import net.minecraft.client.render.entity.animation.Keyframe;
import net.minecraft.client.render.entity.animation.Transformation;

public class GlacialBearAnimations {
    public static final Animation IDLE = Animation.Builder.create(3.0F).looping()
        .addBoneAnimation("bodyback", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bodyback", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg3", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg3", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -0.2608F, 0.7432F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg5", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg5", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -0.2752F, 0.3272F), Transformation.Interpolations.CUBIC),
            new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone4", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("middle", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(1.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bv", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(3.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bv", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -0.0044F, -0.25F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg2", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg2", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -0.1608F, 0.2432F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone2", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg4", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg4", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, -0.2643F, 0.0774F), Transformation.Interpolations.CUBIC),
            new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone3", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(3.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone3", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.5F, AnimationHelper.createTranslationalVector(0.0F, 0.2287F, 0.2055F), Transformation.Interpolations.CUBIC),
            new Keyframe(3.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .build();

    public static final Animation WALK = Animation.Builder.create(1.25F).looping()
        .addBoneAnimation("bodyback", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.375F, AnimationHelper.createRotationalVector(0.0349F, -2.9997F, -1.0012F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(-0.0523F, -0.0014F, -2.9995F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg3", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(-10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg3", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, -0.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 1.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.75F, 0.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(27.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5833F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.2527F, 0.6604F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg5", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(-10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg5", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, -2.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, -0.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.75F, -1.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, -2.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, -2.5F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone4", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(10.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone4", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 0.4781F, 0.1465F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bv", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(1.001F, 2.4996F, -2.9563F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4167F, AnimationHelper.createRotationalVector(0.3299F, 2.4548F, -4.4571F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createRotationalVector(-0.8565F, -0.5489F, 2.5573F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(1.001F, 2.4996F, -2.9563F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bv", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(-0.011F, -0.2497F, -0.004F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg2", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg2", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 1.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.75F, 0.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, -0.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, 1.5F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone2", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(32.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createRotationalVector(27.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createRotationalVector(13.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone2", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, -0.5189F, 1.1648F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -0.2776F, 1.0237F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, -0.6561F, 0.9053F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg4", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg4", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.75F, -1.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.0F, 1.25F, -1.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 0.75F, -2.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, -2.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.25F, -0.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.75F, -1.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone3", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createRotationalVector(22.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9167F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0833F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone3", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0833F, AnimationHelper.createTranslationalVector(0.0F, 0.2524F, -0.0346F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .build();

    public static final Animation ATTACK = Animation.Builder.create(1.25F)
        .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(40.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createRotationalVector(32.4731F, -1.6067F, 1.9156F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bodyback", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(-40.4325F, -7.6443F, 6.4664F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-37.6076F, 3.8282F, -3.2187F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-20.1076F, 3.8282F, -3.2187F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.1667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg3", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(-154.0877F, -14.6378F, 64.8714F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createRotationalVector(-83.795F, -4.8552F, 66.3136F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(-75.5734F, -15.1258F, 38.8307F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg3", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createTranslationalVector(9.0F, 10.0F, 14.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(-2.0F, 10.0F, 1.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(1.0F, 3.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.75F, AnimationHelper.createRotationalVector(62.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(52.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 4.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 2.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg5", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(-5.0F, -12.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createRotationalVector(-4.8994F, 4.9358F, -1.5074F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone4", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone4", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("middle", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(5.0F, 0.0F, -2.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(5.0F, 0.0F, -2.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bv", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(7.1566F, -2.2494F, 17.3593F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createRotationalVector(7.3873F, 1.2988F, -9.9162F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(7.3873F, 1.2988F, -9.9162F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg2", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(-1.929F, 5.6407F, -3.158F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createRotationalVector(-43.9169F, 50.9635F, -46.8893F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(-7.3787F, 47.2626F, -20.4333F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg2", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createTranslationalVector(2.9729F, 15.1435F, 2.8187F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(-6.1577F, 10.8856F, 5.4368F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(-5.0F, 2.0F, 2.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone2", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(4.9244F, 0.8672F, -9.9627F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createRotationalVector(17.78F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone2", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0348F, -0.8156F, 0.7639F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg4", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(-7.5F, -10.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.875F, AnimationHelper.createRotationalVector(7.5F, 10.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg4", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createTranslationalVector(-0.0057F, 0.2479F, 0.0321F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.875F, AnimationHelper.createTranslationalVector(-0.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone3", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone3", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createTranslationalVector(0.0F, -0.9659F, 0.2588F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.875F, AnimationHelper.createTranslationalVector(0.0F, 0.0192F, 0.1133F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone6", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.0F, 5.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone6", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, 1.0F, -2.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(-2.0834F, 1.0F, -0.909F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0833F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .build();

    public static final Animation RUN = Animation.Builder.create(0.625F).looping()
        .addBoneAnimation("bodyback", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg3", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-27.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.125F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createRotationalVector(42.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(-27.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg3", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.4204F, 0.8688F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.125F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 1.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.0F, -0.19F, 1.12F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 1.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, 0.4204F, 0.8688F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.2083F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.2917F, AnimationHelper.createRotationalVector(-8.44F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4167F, AnimationHelper.createRotationalVector(35.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg5", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-40.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.0833F, AnimationHelper.createRotationalVector(-10.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.2917F, AnimationHelper.createRotationalVector(45.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5417F, AnimationHelper.createRotationalVector(-52.5F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(-40.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("leg5", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -1.122F, -2.8609F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.0833F, AnimationHelper.createTranslationalVector(0.0F, -1.4872F, -1.8042F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 0.5074F, -1.9026F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0.0F, 0.5289F, -0.9175F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0.0F, -0.0535F, -1.924F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, -1.122F, -2.8609F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone4", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.0833F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-17.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.375F, AnimationHelper.createRotationalVector(40.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("middle", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("middle", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.1082F, 0.4881F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0.0F, 0.0598F, -0.5087F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, 0.1082F, 0.4881F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bv", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bv", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createTranslationalVector(0.0F, -2.0066F, -0.9867F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg2", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-35.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(-15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4583F, AnimationHelper.createRotationalVector(40.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg2", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.1305F, -0.9914F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 0.0023F, 0.0376F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, -1.9974F, 0.1426F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0.0F, -1.0065F, 0.0264F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, -0.1305F, -0.9914F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone2", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.1667F, AnimationHelper.createRotationalVector(25.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(17.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.375F, AnimationHelper.createRotationalVector(35.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4583F, AnimationHelper.createRotationalVector(44.17F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg4", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-55.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.1667F, AnimationHelper.createRotationalVector(-25.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.375F, AnimationHelper.createRotationalVector(30.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(-55.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("leg4", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -0.1305F, -3.9914F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, -0.9987F, -2.9487F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, -0.201F, -3.0285F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.375F, AnimationHelper.createTranslationalVector(0.0F, 0.506F, -1.7567F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0.0F, 0.6096F, -2.6227F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, -0.1305F, -3.9914F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone3", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(27.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.1667F, AnimationHelper.createRotationalVector(26.25F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(5.23F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createRotationalVector(40.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4583F, AnimationHelper.createRotationalVector(45.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(27.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone6", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4167F, AnimationHelper.createRotationalVector(-7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(-7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone6", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 3.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4167F, AnimationHelper.createTranslationalVector(0.0F, 3.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createTranslationalVector(0.0F, 3.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .build();

    public static final Animation SPAWN = Animation.Builder.create(2.0F)
        .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(15.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createRotationalVector(20.18F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(-30.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createRotationalVector(-30.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createTranslationalVector(0.0F, -0.9409F, -0.3388F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bodyback", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-10.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.4583F, AnimationHelper.createRotationalVector(-2.78F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bodyback", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.4583F, AnimationHelper.createTranslationalVector(0.0F, 3.8874F, -0.4696F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, -1.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, -0.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("left_leg_front", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-52.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.125F, AnimationHelper.createRotationalVector(-52.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(-52.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.375F, AnimationHelper.createRotationalVector(-70.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createRotationalVector(-37.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-50.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.2083F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_leg_front", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 3.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createTranslationalVector(0.0F, 2.9365F, 2.002F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 2.4306F, 2.1014F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.2083F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_calf_front", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createRotationalVector(45.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createRotationalVector(52.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.2083F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_calf_front", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createTranslationalVector(0.0F, -0.7531F, 0.6579F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, -0.7531F, 0.6579F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_leg_back", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(50.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.1667F, AnimationHelper.createRotationalVector(54.84F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.375F, AnimationHelper.createRotationalVector(-41.48F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(-33.84F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-35.75F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_leg_back", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 3.7108F, -1.4933F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_calf_back", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.125F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(32.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(29.0649F, -2.6287F, 1.3995F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(32.6986F, -2.0445F, 1.0885F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_calf_back", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("middle", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createRotationalVector(5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createRotationalVector(7.34F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.2083F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bv", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(17.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.3333F, AnimationHelper.createRotationalVector(7.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createRotationalVector(10.13F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(12.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.2083F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createRotationalVector(-5.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bv", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.7083F, AnimationHelper.createTranslationalVector(0.0F, 0.0327F, -0.7493F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("right_leg_front", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-52.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.125F, AnimationHelper.createRotationalVector(-52.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(-52.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.375F, AnimationHelper.createRotationalVector(-70.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createRotationalVector(-37.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-50.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.2083F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createRotationalVector(2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_leg_front", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 2.0F, 3.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createTranslationalVector(0.0F, 2.9365F, 2.002F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 2.4306F, 2.1014F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.2083F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, 0.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_calf_front", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.1667F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createRotationalVector(45.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createRotationalVector(52.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.2083F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_calf_front", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createTranslationalVector(0.0F, -0.7531F, 0.6579F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, -0.7531F, 0.6579F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_leg_back", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(50.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.1667F, AnimationHelper.createRotationalVector(54.84F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.375F, AnimationHelper.createRotationalVector(-41.48F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(-33.84F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-35.75F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_leg_back", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.1667F, AnimationHelper.createTranslationalVector(0.0F, 3.7108F, -1.4933F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_calf_back", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.125F, AnimationHelper.createRotationalVector(20.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(32.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(29.0649F, 2.6287F, -1.3995F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(32.6986F, 2.0445F, -1.0885F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_calf_back", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone6", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(-40.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone6", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, -46.0F, 50.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.2917F, AnimationHelper.createTranslationalVector(0.0F, 15.0F, 29.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5417F, AnimationHelper.createTranslationalVector(0.0F, -4.0F, 4.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bone6", new Transformation(Transformation.Targets.SCALE,
            new Keyframe(0.0F, AnimationHelper.createScalingVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.0833F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createScalingVector(1.0F, 1.0F, 1.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("jawdown", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.875F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.1667F, AnimationHelper.createRotationalVector(82.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.25F, AnimationHelper.createRotationalVector(85.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.3333F, AnimationHelper.createRotationalVector(82.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.4167F, AnimationHelper.createRotationalVector(85.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.5F, AnimationHelper.createRotationalVector(82.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.5833F, AnimationHelper.createRotationalVector(85.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.6667F, AnimationHelper.createRotationalVector(82.5F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createRotationalVector(85.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("jawdown", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.875F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.2083F, AnimationHelper.createTranslationalVector(0.0F, -0.7781F, 1.2327F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, -0.7781F, 1.2327F), Transformation.Interpolations.LINEAR),
            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("jawup", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.9583F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("jawup", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.1F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.2083F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.1F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.75F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -1.1F), Transformation.Interpolations.CUBIC),
            new Keyframe(2.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, -0.1F), Transformation.Interpolations.CUBIC)
        ))
        .build();

    public static final Animation DEATH = Animation.Builder.create(1.25F)
        .addBoneAnimation("head", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(4.962F, 2.5188F, -0.4356F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(1.3529F, -3.0593F, 18.4109F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createRotationalVector(1.5353F, -3.1335F, 25.8985F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0417F, AnimationHelper.createRotationalVector(22.7691F, 12.23F, -19.0224F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("head", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(-0.9966F, 0.0F, -0.082F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bodyback", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -2.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -2.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -5.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("bodyback", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.7083F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_leg_front", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-6.6968F, -0.8607F, -1.736F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -2.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-13.8283F, 10.8315F, -8.8174F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(-28.732F, 7.8614F, -26.252F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-28.732F, 7.8614F, -33.752F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.0F, AnimationHelper.createRotationalVector(-26.1161F, 14.91F, 36.7655F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_leg_front", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(1.0F, AnimationHelper.createTranslationalVector(-2.0649F, -3.8581F, -2.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("left_calf_front", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(7.4929F, -0.3262F, 4.9786F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(8.7346F, 6.2893F, 22.2568F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.625F, AnimationHelper.createRotationalVector(12.4073F, 8.9333F, 8.4304F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(7.3258F, 12.7638F, -5.6757F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.875F, AnimationHelper.createRotationalVector(23.7574F, 1.9884F, 16.3707F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_calf_front", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.6667F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.875F, AnimationHelper.createTranslationalVector(-1.0699F, -0.1558F, 0.9116F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_leg_back", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(-11.9301F, -8.3842F, -5.7999F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, -7.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-12.4539F, -1.0809F, -12.382F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-7.2467F, 10.2126F, 46.8514F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("left_leg_back", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(-2.2044F, -2.8532F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("left_calf_back", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(9.7346F, -0.5238F, 5.3996F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 10.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7917F, AnimationHelper.createRotationalVector(5.0577F, 9.9807F, -14.5581F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9583F, AnimationHelper.createRotationalVector(7.8308F, 8.0008F, 3.0485F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("middle", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, -2.5F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 2.5F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bv", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, -2.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(0.0F, 2.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(0.0F, 2.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createRotationalVector(0.0F, 2.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0.0F, 7.5F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_leg_front", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.2083F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 2.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, -27.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7917F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, -32.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9167F, AnimationHelper.createRotationalVector(-12.5F, 0.0F, 2.5F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_leg_front", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.2083F, AnimationHelper.createTranslationalVector(0.0F, -1.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5833F, AnimationHelper.createTranslationalVector(0.5F, -3.0F, 0.0F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_calf_front", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.2083F, AnimationHelper.createRotationalVector(22.3964F, 1.6189F, -7.3241F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(24.6775F, -5.8435F, -18.4125F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.5F, AnimationHelper.createRotationalVector(22.316F, -2.154F, 9.7675F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7083F, AnimationHelper.createRotationalVector(22.3964F, -1.6189F, 7.3241F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.7917F, AnimationHelper.createRotationalVector(22.0869F, 3.2113F, -14.6598F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9583F, AnimationHelper.createRotationalVector(22.316F, -2.154F, 9.7675F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_calf_front", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(1.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5F, AnimationHelper.createTranslationalVector(1.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5833F, AnimationHelper.createTranslationalVector(-0.25F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("right_leg_back", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, -2.5F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(-2.5F, 0.0F, -27.5F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createRotationalVector(-2.4407F, -0.5409F, -39.9884F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9583F, AnimationHelper.createRotationalVector(15.9956F, 15.8563F, 8.746F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_leg_back", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.9583F, AnimationHelper.createTranslationalVector(0.9278F, -1.0673F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("right_calf_back", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.0F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(2.4977F, -0.1088F, -0.0072F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5833F, AnimationHelper.createRotationalVector(2.4764F, -0.435F, 7.4858F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createRotationalVector(2.5118F, 0.111F, -5.0024F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.9583F, AnimationHelper.createRotationalVector(2.5012F, 4.6686F, 5.206F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("right_calf_back", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.0F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.9583F, AnimationHelper.createTranslationalVector(-0.4988F, -0.0068F, -0.0346F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone6", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.25F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.7917F, AnimationHelper.createRotationalVector(0.0F, 0.0F, -94.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("bone6", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.25F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.5417F, AnimationHelper.createTranslationalVector(-5.05F, -2.5F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.75F, AnimationHelper.createTranslationalVector(-12.0F, -14.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(0.8333F, AnimationHelper.createTranslationalVector(-12.0F, -13.5F, 0.0F), Transformation.Interpolations.LINEAR),
            new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(-12.0F, -14.0F, 0.0F), Transformation.Interpolations.LINEAR)
        ))
        .addBoneAnimation("jawdown", new Transformation(Transformation.Targets.ROTATE,
            new Keyframe(0.9167F, AnimationHelper.createRotationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.125F, AnimationHelper.createRotationalVector(47.5F, 0.0F, 12.5F), Transformation.Interpolations.CUBIC)
        ))
        .addBoneAnimation("jawdown", new Transformation(Transformation.Targets.TRANSLATE,
            new Keyframe(0.9167F, AnimationHelper.createTranslationalVector(0.0F, 0.0F, 0.0F), Transformation.Interpolations.CUBIC),
            new Keyframe(1.125F, AnimationHelper.createTranslationalVector(-0.3687F, 0.0312F, -0.419F), Transformation.Interpolations.CUBIC)
        ))
        .build();
}

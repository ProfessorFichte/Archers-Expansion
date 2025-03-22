package com.archers_expansion.mixin;

import com.archers_expansion.effect.Effects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.predicate.entity.EntityPredicates;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.more_rpg_classes.util.CustomMethods;
import net.spell_engine.utils.TargetHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.Box;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow
    public abstract boolean hasStatusEffect(StatusEffect effect);

    private static boolean isProtected(Entity target, LivingEntity caster) {
        var relation = TargetHelper.getRelation(caster, target);
        switch (relation) {
            case FRIENDLY, SEMI_FRIENDLY -> {
                return true;
            }
            case NEUTRAL, MIXED, HOSTILE -> {
                return false;
            }
        }
        return false;
    }

    @Inject(method = "jump", at = @At("HEAD"), cancellable = true)
    public void jump_HEAD_NoJumpingWhileFrozen(CallbackInfo ci) {
        if (hasStatusEffect(Effects.PIN_DOWN)) {
            ci.cancel();
        }
    }

    @Inject(method = "onDeath", at = @At("TAIL"))
    public void onDeath$wintersGrip(CallbackInfo ci) {
        if (hasStatusEffect(Effects.WINTERS_GRASP)) {
            LivingEntity entity = (LivingEntity)(Object)this;
            float range = 3.0F;
            Box radius = new Box(entity.getX() + range,
                    entity.getY() + (float) range / 3,
                    entity.getZ() + range,
                    entity.getX() - range,
                    entity.getY() - (float) range / 3,
                    entity.getZ() - range);
            for(Entity entities : entity.getEntityWorld().getOtherEntities(entity, radius, EntityPredicates.VALID_LIVING_ENTITY)) {
                if (entities != null) {
                    if (entities instanceof LivingEntity targets && !isProtected(targets, entity)) {
                        targets.addStatusEffect(new StatusEffectInstance(MRPGCEffects.FROZEN_SOLID, 60, 0, false, false, true));
                    }
                }
            }
        }
    }
}

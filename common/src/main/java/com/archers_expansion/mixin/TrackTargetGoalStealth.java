package com.archers_expansion.mixin;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.effect.ArchersEffects;
import net.minecraft.entity.ai.goal.TrackTargetGoal;
import net.minecraft.entity.mob.MobEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/// This code is under ARR license, permission to used it granted by Daedelus
// https://github.com/ZsoltMolnarrr/Rogues/blob/1.21.1/src/main/java/net/rogues/mixin/TrackTargetGoalStealth.java
@Mixin(TrackTargetGoal.class)
public class TrackTargetGoalStealth {
    @Shadow
    @Final
    protected MobEntity mob;

    /**
     * Both of these mixins achieve kinda the same thing.
     * The one with range is theoretically more elegant.
     */

//    @Inject(method = "shouldContinue", at = @At("HEAD"), cancellable = true)
//    private void shouldContinue_HEAD(CallbackInfoReturnable<Boolean> cir) {
//        var target = mob.getTarget();
//        if (target != null && target.hasStatusEffect(Effects.STEALTH)) {
//            cir.setReturnValue(false);
//        }
//    }

    @Inject(method = "getFollowRange", at = @At("HEAD"), cancellable = true)
    private void getFollowRange_HEAD(CallbackInfoReturnable<Double> cir) {
        var target = mob.getTarget();
        if (target != null
                && (target.hasStatusEffect(ArchersEffects.getEntry(ArchersEffects.INFILTRATORS_ARROW)) )) {
            cir.setReturnValue(ArchersExpansionMod.tweaksConfig.value.stealth_follow_range);
            cir.cancel();
        }
    }
}

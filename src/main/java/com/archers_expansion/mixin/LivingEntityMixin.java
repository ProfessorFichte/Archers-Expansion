package com.archers_expansion.mixin;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.effect.Effects;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.predicate.entity.EntityPredicates;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.spell_engine.internals.target.EntityRelations;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import net.minecraft.util.math.Box;

@Mixin(LivingEntity.class)
public class LivingEntityMixin {

    private static boolean isProtected(Entity target, LivingEntity attacker) {
        var relation = EntityRelations.getRelation(attacker, target);
        switch (relation) {
            case ALLY, FRIENDLY -> {
                return true;
            }
            case NEUTRAL, MIXED, HOSTILE -> {
                return false;
            }
        }
        return false;
    }


    @Inject(method = "onDeath", at = @At("TAIL"))
    public void onDeath$wintersGrip(CallbackInfo ci) {
        LivingEntity entity = (LivingEntity)(Object)this;
        if (entity.hasStatusEffect(Effects.WINTERS_GRASP.registryEntry)) {
            ArchersExpansionMod.LOGGER.info("JAAAAA");
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
                        targets.addStatusEffect(new StatusEffectInstance(MRPGCEffects.FROZEN_SOLID.registryEntry, 60, 0, false, false, true));
                    }
                }
            }
        }
    }

}

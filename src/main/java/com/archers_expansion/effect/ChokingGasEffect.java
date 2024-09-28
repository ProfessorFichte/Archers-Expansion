package com.archers_expansion.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.Box;
import net.more_rpg_classes.effect.MRPGCEffects;
import net.spell_engine.utils.TargetHelper;

public class ChokingGasEffect extends StatusEffect {

    protected ChokingGasEffect(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        LivingEntity attacker = entity.getLastAttacker();
        float range = 3.5F;
        Box radius = new Box(entity.getX() + range,
                entity.getY() + (float) range / 3,
                entity.getZ() + range,
                entity.getX() - range,
                entity.getY() - (float) range / 3,
                entity.getZ() - range);

        for(Entity entities : entity.getEntityWorld().getOtherEntities(entity, radius, EntityPredicates.VALID_LIVING_ENTITY)){
            if (entities != null) {
                if(entities instanceof LivingEntity target){
                    var relation = TargetHelper.getRelation(attacker,target);
                    if(relation == TargetHelper.Relation.HOSTILE || relation == TargetHelper.Relation.MIXED || relation == TargetHelper.Relation.NEUTRAL) {
                        target.addStatusEffect(new StatusEffectInstance(Effects.CHOKING_POISON,100,0,false,false,true));
                    }
                }
            }
        }
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        float damage = 1.0F;
        if(entity.hasStatusEffect(MRPGCEffects.BLEEDING)){
            damage = damage + 0.5F;
        }
        entity.damage(entity.getDamageSources().magic(), damage);
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        int i;
        i = 30 >> amplifier;
        if (i > 0) {
            return duration % i == 0;
        } else {
            return true;
        }
    }
}

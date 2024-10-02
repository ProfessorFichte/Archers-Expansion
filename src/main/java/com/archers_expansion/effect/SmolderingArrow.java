package com.archers_expansion.effect;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Box;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;
import net.spell_engine.utils.TargetHelper;

public class SmolderingArrow extends StatusEffect {
    public static final ParticleBatch smoke = new ParticleBatch(
            "smoke",
            ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
            ParticleBatch.Rotation.LOOK, 50, 0.1F, 0.8F, 360);


    protected SmolderingArrow(StatusEffectCategory category, int color) {
        super(category, color);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        entity.damage(entity.getDamageSources().onFire(),1.0F *(amplifier + 1));
        LivingEntity attacker = entity.getLastAttacker();
        float range_inc = (amplifier + 1) * 0.1F;
        float range = 1.5F + range_inc;

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
                        target.damage(entity.getDamageSources().onFire(),1.0F *(amplifier + 1));
                        if (!entity.getWorld().isClient()) {
                            ParticleHelper.sendBatches(target, new ParticleBatch[]{smoke});
                            target.setFireTicks(40);
                        }
                    }
                }
            }
        }
    }
}

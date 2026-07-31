package com.archers_expansion.entity;

import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.datagen.SpellBuilder.Placements;
import net.spell_engine.api.spell.Spell.Impact.Action.Summon;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.spell.fx.VFX;
import net.spell_engine.api.spell.summon.AttributeScaling;
import net.spell_engine.api.spell.summon.SummonBehaviour;
import net.spell_engine.api.spell.summon.SummonedEntityConfig;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;

import java.util.ArrayList;
import java.util.List;

public class ArcherExpansionSummons {

    public static SummonedEntityConfig.Entry defaults() {
        var e = new SummonedEntityConfig.Entry();
        e.common = new SummonedEntityConfig.CommonAttributes(30, 0.25, 6);
        e.common.follow_range = 16;
        e.custom.add(new SummonedEntityConfig.CustomAttribute(
                EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE.getIdAsString(), 0.5));
        // GlacialBearEntityModel reads GENERIC_ATTACK_SPEED for its attack animation, but SummonedEntity.createAttributes() doesn't register it, so it must be seeded here or it throws.
        e.custom.add(new SummonedEntityConfig.CustomAttribute(
                EntityAttributes.GENERIC_ATTACK_SPEED.getIdAsString(), 1.0));
        return e;
    }

    public static Summon summon() {
        var b = new SummonBehaviour();
        b.lifespan.spawn_ticks = 20;
        b.lifespan.active_seconds = 45;
        b.lifespan.despawn_ticks = 20;

        b.movement.follow = new SummonBehaviour.Movement.Follow();
        b.movement.follow.teleport_after_distance = 24F;
        b.movement.collision = SummonBehaviour.Movement.CollisionMode.ENEMIES;

        b.targeting.attack_with_owner = true;
        b.targeting.revenge = true;
        b.targeting.automatic_targeting = SummonBehaviour.Targeting.AutoTarget.HOSTILE;

        var attack = new SummonBehaviour.Action.MeleeAttack();
        attack.max_range = 0; // no cap - chase any acquired target, like the Spirit Wolf
        attack.speed = 0.9F;
        attack.duration = 25;
        attack.windup = 0.5F;
        attack.radius = 2.5F;
        attack.movement_speed = 1.0F;
        attack.swing_sound = new Sound("minecraft:entity.polar_bear.warning");
        attack.impact_sound = new Sound("spell_engine:generic_frost_impact");
        b.actions = List.of(SummonBehaviour.Action.attack(attack));

        b.sounds.spawn = new Sound("minecraft:entity.polar_bear.ambient");
        b.sounds.despawn = new Sound("minecraft:entity.polar_bear.hurt");
        b.sounds.hurt = new Sound("minecraft:entity.polar_bear.hurt");
        b.sounds.death = new Sound("minecraft:entity.polar_bear.death");
        b.sounds.ambient = new Sound("minecraft:entity.polar_bear.ambient");
        b.sounds.step = new Sound("minecraft:entity.polar_bear.step");

        b.spawn_fx = new VFX();
        b.spawn_fx.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.MagicParticles.get(
                        SpellEngineParticles.MagicParticles.Shape.FROST,
                        SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        40, 0.2F, 0.5F).color(Color.BLUE.toRGBA()),
                new ParticleBatch(SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        30, 0.1F, 0.3F)
        };
        b.despawn_fx = new VFX();
        b.despawn_fx.particles = new ParticleBatch[]{
                new ParticleBatch(SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        25, 0.1F, 0.3F).color(Color.BLUE.toRGBA())
        };

        var placements = List.of(Placements.pointAtAngle(2.0F, 0F));

        var summon = new Summon(
                PolarBearEntity.ENTITY_TYPE.getRegistryEntry().getKey().get().getValue().toString(),
                b, placements, 1);
        summon.attribute_scaling.entries = frostScaling();
        return summon;
    }

    private static List<AttributeScaling.Entry> frostScaling() {
        var s = MoreSpellSchools.FROST_RANGED.attributeEntry.getIdAsString();
        var entries = new ArrayList<AttributeScaling.Entry>();
        entries.add(scalingEntry(EntityAttributes.GENERIC_MAX_HEALTH.getIdAsString(), s, 0, 2.0));
        entries.add(scalingEntry(EntityAttributes.GENERIC_ATTACK_DAMAGE.getIdAsString(), s, 0, 0.3));
        return entries;
    }

    private static AttributeScaling.Entry scalingEntry(String targetAttribute, String ownerAttribute,
                                                        double base, double coefficient) {
        var entry = new AttributeScaling.Entry();
        entry.attribute_id = targetAttribute;
        entry.modifiers = List.of(new AttributeScaling.Entry.OwnerModifier(
                ownerAttribute, EntityAttributeModifier.Operation.ADD_VALUE, base, coefficient));
        return entry;
    }
}

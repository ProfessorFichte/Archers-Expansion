package com.archers_expansion.entity;

import com.archers_expansion.ArchersExpansionMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.summon.SummonedEntities;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ModEntitiesRegistry {

    public static final Identifier POLAR_BEAR_ID = Identifier.of(MOD_ID, "spell_polar_bear");

    public static void registerEntities() {
        ExplosiveBarrelEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "explosive_barrel"),
                EntityType.Builder.<ExplosiveBarrelEntity>create(ExplosiveBarrelEntity::new, SpawnGroup.MISC)
                        .dimensions(1.0F, 1.0F)
                        .makeFireImmune()
                        .maxTrackingRange(64)
                        .trackingTickInterval(10)
                        .build("explosive_barrel")
        );

        AlterEgoEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "alter_ego"),
                EntityType.Builder.create(AlterEgoEntity::new, SpawnGroup.CREATURE)
                        .dimensions(0.6F, 1.8F)
                        .maxTrackingRange(64)
                        .trackingTickInterval(2)
                        .build("alter_ego")
        );

        FrozenFussiladeEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "frozen_fusillade"),
                EntityType.Builder.<FrozenFussiladeEntity>create(FrozenFussiladeEntity::new, SpawnGroup.MISC)
                        .dimensions(6F, 0.5F)
                        .makeFireImmune()
                        .maxTrackingRange(128)
                        .trackingTickInterval(20)
                        .build("frozen_fusillade")
        );

        PoisonFlaskProjectile.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "poison_flask"),
                EntityType.Builder.<PoisonFlaskProjectile>create(PoisonFlaskProjectile::new, SpawnGroup.MISC)
                        .dimensions(0.6F, 0.6F)
                        .maxTrackingRange(64)
                        .trackingTickInterval(10)
                        .build("poison_flask")
        );

        PolarBearEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                POLAR_BEAR_ID,
                EntityType.Builder.create(PolarBearEntity::new, SpawnGroup.CREATURE)
                        .dimensions(1.4F, 1.4F)
                        .maxTrackingRange(64)
                        .trackingTickInterval(2)
                        .build("spell_polar_bear")
        );

        SummonedEntities.registerAttributes(POLAR_BEAR_ID, PolarBearEntity.ENTITY_TYPE, ArchersExpansionMod.summonConfig.value::entryFor);
    }

    public static void registerEntityAttributes(AttributeRegistrar registrar) {
        registrar.register(
            AlterEgoEntity.ENTITY_TYPE,
            AlterEgoEntity.createAlterEgoAttributes()
        );
    }

    @FunctionalInterface
    public interface AttributeRegistrar {
        void register(net.minecraft.entity.EntityType<? extends net.minecraft.entity.LivingEntity> entityType,
                     net.minecraft.entity.attribute.DefaultAttributeContainer.Builder builder);
    }
}

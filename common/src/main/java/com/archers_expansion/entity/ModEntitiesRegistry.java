package com.archers_expansion.entity;

import com.archers_expansion.ArchersExpansionMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.summon.SummonedEntities;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ModEntitiesRegistry {

    public static final Identifier POLAR_BEAR_ID = Identifier.of(MOD_ID, "spell_polar_bear");

    public static void registerEntities() {
        WintersGripEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "winters_grip"),
                FabricEntityTypeBuilder.<WintersGripEntity>create(SpawnGroup.MISC, WintersGripEntity::new)
                        .dimensions(EntityDimensions.changing(6F, 0.5F))
                        .fireImmune()
                        .trackRangeBlocks(128)
                        .trackedUpdateRate(20)
                        .build()
        );

        ExplosiveBarrelEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "explosive_barrel"),
                FabricEntityTypeBuilder.<ExplosiveBarrelEntity>create(SpawnGroup.MISC, ExplosiveBarrelEntity::new)
                        .dimensions(EntityDimensions.fixed(1.0F, 1.0F))
                        .fireImmune()
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(10)
                        .build()
        );

        AlterEgoEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "alter_ego"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AlterEgoEntity::new)
                        .dimensions(EntityDimensions.fixed(0.6F, 1.8F))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(2)
                        .build()
        );

        FrozenFussiladeEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "frozen_fusillade"),
                FabricEntityTypeBuilder.<FrozenFussiladeEntity>create(SpawnGroup.MISC, FrozenFussiladeEntity::new)
                        .dimensions(EntityDimensions.changing(6F, 0.5F))
                        .fireImmune()
                        .trackRangeBlocks(128)
                        .trackedUpdateRate(20)
                        .build()
        );

        PoisonFlaskProjectile.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "poison_flask"),
                FabricEntityTypeBuilder.<PoisonFlaskProjectile>create(SpawnGroup.MISC, PoisonFlaskProjectile::new)
                        .dimensions(EntityDimensions.fixed(0.6F, 0.6F))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(10)
                        .build()
        );

        InfiltratorsArrowProjectile.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "infiltrators_arrow"),
                FabricEntityTypeBuilder.<InfiltratorsArrowProjectile>create(SpawnGroup.MISC, InfiltratorsArrowProjectile::new)
                        .dimensions(EntityDimensions.fixed(0.5F, 0.5F))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(20)
                        .build()
        );

        PolarBearEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                POLAR_BEAR_ID,
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PolarBearEntity::new)
                        .dimensions(EntityDimensions.changing(1.4F, 1.4F))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(2)
                        .build()
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

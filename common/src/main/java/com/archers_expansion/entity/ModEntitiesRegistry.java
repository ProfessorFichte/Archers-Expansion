package com.archers_expansion.entity;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

/**
 * Registry for all entities in the Archers Expansion mod.
 */
public class ModEntitiesRegistry {

    /**
     * Registers all entities for the mod.
     * Should be called during mod initialization.
     */
    public static void registerEntities() {
        // Register WintersGripEntity
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

        // Register ExplosiveBarrelEntity
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

        // Register AlterEgoEntity
        AlterEgoEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "alter_ego"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, AlterEgoEntity::new)
                        .dimensions(EntityDimensions.fixed(0.6F, 1.8F))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(2)
                        .build()
        );

        // Register PoisonFlaskProjectile
        PoisonFlaskProjectile.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "poison_flask"),
                FabricEntityTypeBuilder.<PoisonFlaskProjectile>create(SpawnGroup.MISC, PoisonFlaskProjectile::new)
                        .dimensions(EntityDimensions.fixed(0.25F, 0.25F))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(10)
                        .build()
        );

        // Register PolarBearEntity
        PolarBearEntity.ENTITY_TYPE = Registry.register(
                Registries.ENTITY_TYPE,
                Identifier.of(MOD_ID, "spell_polar_bear"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, PolarBearEntity::new)
                        .dimensions(EntityDimensions.fixed(1.4F, 1.4F))
                        .trackRangeBlocks(64)
                        .trackedUpdateRate(2)
                        .build()
        );
    }
}

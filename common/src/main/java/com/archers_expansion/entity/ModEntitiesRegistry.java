package com.archers_expansion.entity;

import com.archers_expansion.ArchersExpansionMod;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.summon.SummonedEntities;

import java.util.LinkedHashMap;
import java.util.Map;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ModEntitiesRegistry {

    public static final Identifier POLAR_BEAR_ID = new Identifier(MOD_ID, "spell_polar_bear");

    public static void registerEntities() {
        entityTypesToRegister().forEach((id, entityType) -> Registry.register(Registries.ENTITY_TYPE, id, entityType));
        registerSummonAttributes();
    }

    /// Builds every entity type and fills the `ENTITY_TYPE` static fields, returning them keyed by the id
    /// they register under. Creation only - nothing is written here, so a loader that registers entity
    /// types itself (Forge, through `RegisterEvent`'s helper) iterates this instead of calling
    /// {@link #registerEntities}, then calls {@link #registerSummonAttributes()}.
    public static Map<Identifier, EntityType<?>> entityTypesToRegister() {
        var types = new LinkedHashMap<Identifier, EntityType<?>>();

        ExplosiveBarrelEntity.ENTITY_TYPE =
                EntityType.Builder.<ExplosiveBarrelEntity>create(ExplosiveBarrelEntity::new, SpawnGroup.MISC)
                        .setDimensions(1.0F, 1.0F)
                        .makeFireImmune()
                        .maxTrackingRange(64)
                        .trackingTickInterval(10)
                        .build("explosive_barrel");
        types.put(new Identifier(MOD_ID, "explosive_barrel"), ExplosiveBarrelEntity.ENTITY_TYPE);

        AlterEgoEntity.ENTITY_TYPE =
                EntityType.Builder.create(AlterEgoEntity::new, SpawnGroup.CREATURE)
                        .setDimensions(0.6F, 1.8F)
                        .maxTrackingRange(64)
                        .trackingTickInterval(2)
                        .build("alter_ego");
        types.put(new Identifier(MOD_ID, "alter_ego"), AlterEgoEntity.ENTITY_TYPE);

        FrozenFussiladeEntity.ENTITY_TYPE =
                EntityType.Builder.<FrozenFussiladeEntity>create(FrozenFussiladeEntity::new, SpawnGroup.MISC)
                        .setDimensions(6F, 0.5F)
                        .makeFireImmune()
                        .maxTrackingRange(128)
                        .trackingTickInterval(20)
                        .build("frozen_fusillade");
        types.put(new Identifier(MOD_ID, "frozen_fusillade"), FrozenFussiladeEntity.ENTITY_TYPE);

        PoisonFlaskProjectile.ENTITY_TYPE =
                EntityType.Builder.<PoisonFlaskProjectile>create(PoisonFlaskProjectile::new, SpawnGroup.MISC)
                        .setDimensions(0.6F, 0.6F)
                        .maxTrackingRange(64)
                        .trackingTickInterval(10)
                        .build("poison_flask");
        types.put(new Identifier(MOD_ID, "poison_flask"), PoisonFlaskProjectile.ENTITY_TYPE);

        PolarBearEntity.ENTITY_TYPE =
                EntityType.Builder.create(PolarBearEntity::new, SpawnGroup.CREATURE)
                        .setDimensions(1.4F, 1.4F)
                        .maxTrackingRange(64)
                        .trackingTickInterval(2)
                        .build("spell_polar_bear");
        types.put(POLAR_BEAR_ID, PolarBearEntity.ENTITY_TYPE);

        return types;
    }

    /// The trailing side effect of {@link #registerEntities}: hands the Polar Bear's summon attributes to
    /// SpellEngine, which buffers them until its own `EntityAttributeCreationEvent` listener flushes them.
    public static void registerSummonAttributes() {
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

package com.archers_expansion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.spell_engine.entity.SpellCloud;

public class FrozenFussiladeEntity extends SpellCloud {
    public static EntityType<FrozenFussiladeEntity> ENTITY_TYPE;

    public FrozenFussiladeEntity(EntityType<? extends SpellCloud> entityType, World world) {
        super(entityType, world);
    }
}
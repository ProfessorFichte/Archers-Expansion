package com.archers_expansion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.world.World;
import net.spell_engine.entity.SpellCloud;

public class WintersGripEntity extends SpellCloud {
    public static EntityType<WintersGripEntity> ENTITY_TYPE;

    public WintersGripEntity(EntityType<? extends SpellCloud> entityType, World world) {
        super(entityType, world);
    }
}

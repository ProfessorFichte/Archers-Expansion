package com.archers_expansion.entity;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import net.spell_engine.entity.SummonedEntity;
import net.spell_power.api.ModifierDefinitions;

import java.util.UUID;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class PolarBearEntity extends SummonedEntity {
    public static EntityType<PolarBearEntity> ENTITY_TYPE;

    /// 1.20.1 attribute modifiers are UUID-keyed (the `Identifier`-keyed API is 1.21+).
    /// SpellEngine derives a stable UUID from an identifier, so the modifier survives a restart.
    private static final Identifier SPEED_BURST_MODIFIER_ID = new Identifier(MOD_ID, "polar_bear_speed_burst");
    private static final UUID SPEED_BURST_MODIFIER_UUID = ModifierDefinitions.uuid(SPEED_BURST_MODIFIER_ID);
    private static final int SPEED_BURST_DURATION_TICKS = 60;
    private static final int SPEED_BURST_COOLDOWN_TICKS = 200;
    private static final double SPEED_BURST_CHARGE_RANGE = 8.0;
    private static final double SPEED_BURST_MULTIPLIER = 0.75;

    private static final TrackedData<Boolean> SPEED_BURST =
            DataTracker.registerData(PolarBearEntity.class, TrackedDataHandlerRegistry.BOOLEAN);

    private int speedBurstEndAge = -1;
    private int nextSpeedBurstAge = 0;

    public PolarBearEntity(EntityType<? extends PolarBearEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    protected void initDataTracker() {
        super.initDataTracker();
        this.getDataTracker().startTracking(SPEED_BURST, false);
    }

    public boolean isSpeedBursting() {
        return this.getDataTracker().get(SPEED_BURST);
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.getWorld().isClient()) {
            tickSpeedBurst();
        }
    }

    private void tickSpeedBurst() {
        var speed = this.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        if (speed == null) return;

        if (speedBurstEndAge >= 0) {
            if (this.age >= speedBurstEndAge) {
                speed.removeModifier(SPEED_BURST_MODIFIER_UUID);
                speedBurstEndAge = -1;
                this.getDataTracker().set(SPEED_BURST, false);
            }
            return;
        }

        if (this.age < nextSpeedBurstAge) return;

        LivingEntity target = this.getTarget();
        if (target == null || !target.isAlive()) return;
        if (this.squaredDistanceTo(target) < SPEED_BURST_CHARGE_RANGE * SPEED_BURST_CHARGE_RANGE) return;

        speed.addTemporaryModifier(new EntityAttributeModifier(
                SPEED_BURST_MODIFIER_UUID, "Polar bear speed burst",
                SPEED_BURST_MULTIPLIER, EntityAttributeModifier.Operation.MULTIPLY_TOTAL));
        speedBurstEndAge = this.age + SPEED_BURST_DURATION_TICKS;
        nextSpeedBurstAge = speedBurstEndAge + SPEED_BURST_COOLDOWN_TICKS;
        this.getDataTracker().set(SPEED_BURST, true);
    }
}

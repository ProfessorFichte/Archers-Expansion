package com.archers_expansion.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.goal.*;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.entity.SpellEntity;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.target.EntityRelations;
import net.spell_power.api.SpellPower;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class PolarBearEntity extends PathAwareEntity implements SpellEntity.Spawned {
    public static EntityType<PolarBearEntity> ENTITY_TYPE;

    private static final TrackedData<String> SPELL_ID_TRACKER = DataTracker.registerData(PolarBearEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> OWNER_ID_TRACKER = DataTracker.registerData(PolarBearEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TIME_TO_LIVE_TRACKER = DataTracker.registerData(PolarBearEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private Identifier spellId;
    private int ownerId;
    private int timeToLive;
    private LivingEntity cachedOwner = null;
    private Identifier frostExplosionSpellId;

    public PolarBearEntity(EntityType<? extends PathAwareEntity> entityType, World world) {
        super(entityType, world);
    }

    public static DefaultAttributeContainer.Builder createPolarBearAttributes() {
        return PathAwareEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 30.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25)
            .add(EntityAttributes.GENERIC_ATTACK_DAMAGE, 6.0)
            .add(EntityAttributes.GENERIC_FOLLOW_RANGE, 16.0);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new SwimGoal(this));
        this.goalSelector.add(1, new MeleeAttackGoal(this, 1.0, true));
        this.goalSelector.add(2, new FollowOwnerGoal(this, 0.9, 10.0F, 2.0F));
        this.goalSelector.add(3, new WanderAroundFarGoal(this, 0.7));
        this.goalSelector.add(4, new LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(5, new LookAroundGoal(this));

        this.targetSelector.add(1, new AttackHostilesGoal(this));
    }

    @Override
    public void onSpawnedBySpell(Args args) {
        var owner = args.owner();
        var spellId = args.spell().getKey().get().getValue();
        var spawn = args.spawnData();

        this.spellId = spellId;
        this.getDataTracker().set(SPELL_ID_TRACKER, this.spellId.toString());
        this.ownerId = owner.getId();
        this.cachedOwner = owner;
        this.getDataTracker().set(OWNER_ID_TRACKER, this.ownerId);
        this.timeToLive = spawn.time_to_live_seconds * 20;
        this.getDataTracker().set(TIME_TO_LIVE_TRACKER, this.timeToLive);

        this.frostExplosionSpellId = Identifier.of(MOD_ID, "frost_explosion");

        var frostPower = SpellPower.getSpellPower(MoreSpellSchools.FROST_RANGED, owner);
        double spellPowerValue = frostPower.randomValue();

        double scaledHealth = Math.max(20.0, Math.min(150.0, 30.0 + (spellPowerValue * 2.0)));
        this.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(scaledHealth);
        this.setHealth((float) scaledHealth);

        double scaledDamage = Math.max(4.0, Math.min(25.0, 6.0 + (spellPowerValue * 0.3)));
        this.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(scaledDamage);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(SPELL_ID_TRACKER, "");
        builder.add(OWNER_ID_TRACKER, 0);
        builder.add(TIME_TO_LIVE_TRACKER, 0);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        var rawSpellId = this.getDataTracker().get(SPELL_ID_TRACKER);
        if (rawSpellId != null && !rawSpellId.isEmpty()) {
            this.spellId = Identifier.of(rawSpellId);
        }
        this.timeToLive = this.getDataTracker().get(TIME_TO_LIVE_TRACKER);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        this.spellId = Identifier.of(nbt.getString("SpellId"));
        this.ownerId = nbt.getInt("OwnerId");
        this.timeToLive = nbt.getInt("TimeToLive");

        this.getDataTracker().set(SPELL_ID_TRACKER, this.spellId.toString());
        this.getDataTracker().set(OWNER_ID_TRACKER, this.ownerId);
        this.getDataTracker().set(TIME_TO_LIVE_TRACKER, this.timeToLive);
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        nbt.putString("SpellId", this.spellId.toString());
        nbt.putInt("OwnerId", this.ownerId);
        nbt.putInt("TimeToLive", this.timeToLive);
    }

    @Override
    public void tick() {
        super.tick();

        var owner = this.getOwner();
        if (owner == null || owner.isRemoved() || !owner.isAlive()) {
            this.discard();
            return;
        }

        if (this.age > this.timeToLive) {
            this.discard();
        }
    }

    @Override
    public boolean tryAttack(Entity target) {
        boolean didAttack = super.tryAttack(target);

        if (didAttack && target instanceof LivingEntity livingTarget) {
            triggerFrostExplosion(livingTarget);
        }

        return didAttack;
    }

    private void triggerFrostExplosion(LivingEntity target) {
        var owner = this.getOwner();
        if (owner == null) return;

        RegistryEntry<Spell> frostSpell = SpellRegistry.from(owner.getWorld())
            .getEntry(this.frostExplosionSpellId)
            .orElse(null);

        if (frostSpell != null) {
            SpellHelper.performImpacts(owner.getWorld(), owner, target, target,
                frostSpell, frostSpell.value().impacts,
                new SpellHelper.ImpactContext().position(target.getPos()));
        }
    }

    private LivingEntity getOwner() {
        if (cachedOwner != null) {
            return cachedOwner;
        }
        var owner = this.getWorld().getEntityById(this.ownerId);
        if (owner instanceof LivingEntity livingOwner) {
            cachedOwner = livingOwner;
            return livingOwner;
        }
        return null;
    }

    private class FollowOwnerGoal extends Goal {
        private final PolarBearEntity bear;
        private final double speed;
        private final float maxDistance;
        private final float minDistance;
        private int updateCountdownTicks;

        public FollowOwnerGoal(PolarBearEntity bear, double speed, float maxDistance, float minDistance) {
            this.bear = bear;
            this.speed = speed;
            this.maxDistance = maxDistance;
            this.minDistance = minDistance;
            this.setControls(java.util.EnumSet.of(Control.MOVE, Control.LOOK));
        }

        @Override
        public boolean canStart() {
            LivingEntity owner = bear.getOwner();
            if (owner == null) return false;
            if (owner.isSpectator()) return false;
            return bear.squaredDistanceTo(owner) >= (minDistance * minDistance);
        }

        @Override
        public boolean shouldContinue() {
            LivingEntity owner = bear.getOwner();
            if (owner == null) return false;

            double stopDistance = minDistance / 2.0;
            return bear.squaredDistanceTo(owner) > (stopDistance * stopDistance);
        }

        @Override
        public void start() {
            this.updateCountdownTicks = 0;
        }

        @Override
        public void stop() {
            bear.getNavigation().stop();
        }

        @Override
        public void tick() {
            LivingEntity owner = bear.getOwner();
            if (owner == null) return;

            bear.getLookControl().lookAt(owner, 10.0F, (float) bear.getMaxLookPitchChange());

            if (--this.updateCountdownTicks <= 0) {
                this.updateCountdownTicks = 20;

                if (bear.getNavigation().isIdle() || bear.squaredDistanceTo(owner) > (maxDistance * maxDistance * 0.5)) {
                    bear.getNavigation().startMovingTo(owner, this.speed);
                }
            }
        }
    }

    private class AttackHostilesGoal extends Goal {
        private final PolarBearEntity bear;
        private LivingEntity targetEntity;

        public AttackHostilesGoal(PolarBearEntity bear) {
            this.bear = bear;
            this.setControls(java.util.EnumSet.of(Control.TARGET));
        }

        @Override
        public boolean canStart() {
            LivingEntity owner = bear.getOwner();
            if (owner == null) return false;

            targetEntity = findNearestHostile();
            return targetEntity != null;
        }

        @Override
        public boolean shouldContinue() {
            if (targetEntity == null || !targetEntity.isAlive()) return false;

            LivingEntity owner = bear.getOwner();
            if (owner == null) return false;

            return bear.squaredDistanceTo(owner) < 256.0;
        }

        @Override
        public void start() {
            bear.setTarget(targetEntity);
        }

        @Override
        public void stop() {
            bear.setTarget(null);
            targetEntity = null;
        }

        private LivingEntity findNearestHostile() {
            LivingEntity owner = bear.getOwner();
            if (owner == null) return null;

            var nearbyEntities = bear.getWorld().getOtherEntities(bear,
                bear.getBoundingBox().expand(16.0),
                entity -> entity instanceof LivingEntity);

            LivingEntity nearest = null;
            double nearestDistance = Double.MAX_VALUE;

            for (Entity entity : nearbyEntities) {
                if (!(entity instanceof LivingEntity livingEntity)) continue;
                if (entity == owner) continue;

                if (!isProtected(owner, entity)) {
                    double distance = bear.squaredDistanceTo(entity);
                    if (distance < nearestDistance) {
                        nearest = livingEntity;
                        nearestDistance = distance;
                    }
                }
            }

            return nearest;
        }

        public boolean isProtected(LivingEntity owner, Entity other) {
            if (owner == null) {
                return false;
            }
            var relation = EntityRelations.getRelation(owner, other);
            switch (relation) {
                case ALLY, FRIENDLY -> {
                    return true;
                }
                case MIXED, HOSTILE, NEUTRAL -> {
                    return false;
                }
            }
            return false;
        }
    }
}

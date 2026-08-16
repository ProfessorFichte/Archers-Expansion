package com.archers_expansion.entity;

import com.archers_expansion.ArchersExpansionMod;
import net.minecraft.sound.SoundEvent;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.fx.ParticleHelper;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.utils.SoundHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.entity.SpellEntity;
import net.spell_engine.internals.target.EntityRelations;

import com.archers_expansion.entity.util.SpellAreaExplosion;

import java.util.UUID;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class AlterEgoEntity extends PathAwareEntity implements SpellEntity.Spawned {
    public static EntityType<AlterEgoEntity> ENTITY_TYPE;

    public static DefaultAttributeContainer.Builder createAlterEgoAttributes() {
        return PathAwareEntity.createMobAttributes()
            .add(EntityAttributes.GENERIC_MAX_HEALTH, 20.0)
            .add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.25);
    }


    private static final TrackedData<String> SPELL_ID_TRACKER = DataTracker.registerData(AlterEgoEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> TIME_TO_LIVE_TRACKER = DataTracker.registerData(AlterEgoEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> COPIED_HEALTH_TRACKER = DataTracker.registerData(AlterEgoEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<String> PLAYER_UUID_TRACKER = DataTracker.registerData(AlterEgoEntity.class, TrackedDataHandlerRegistry.STRING);

    private static final TrackedData<ItemStack> HELMET_TRACKER = DataTracker.registerData(AlterEgoEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<ItemStack> CHESTPLATE_TRACKER = DataTracker.registerData(AlterEgoEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<ItemStack> LEGGINGS_TRACKER = DataTracker.registerData(AlterEgoEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<ItemStack> BOOTS_TRACKER = DataTracker.registerData(AlterEgoEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<ItemStack> MAIN_HAND_TRACKER = DataTracker.registerData(AlterEgoEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);
    private static final TrackedData<ItemStack> OFF_HAND_TRACKER = DataTracker.registerData(AlterEgoEntity.class, TrackedDataHandlerRegistry.ITEM_STACK);

    private Identifier spellId;
    private UUID ownerUuid;
    private int timeToLive;
    private LivingEntity cachedOwner = null;
    private UUID playerUuid;
    private float copiedHealth;
    private boolean hasExploded = false;
    private LivingEntity lastAttacker = null;

    public AlterEgoEntity(EntityType<? extends PathAwareEntity> type, World world) {
        super(type, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(0, new net.minecraft.entity.ai.goal.SwimGoal(this));
        this.goalSelector.add(1, new FleeFromHostilesGoal(this, 1.0, 8.0F, 10.0F));
        this.goalSelector.add(2, new ProvokeHostilesGoal(this));
        this.goalSelector.add(3, new net.minecraft.entity.ai.goal.LookAtEntityGoal(this, PlayerEntity.class, 8.0F));
        this.goalSelector.add(4, new net.minecraft.entity.ai.goal.WanderAroundFarGoal(this, 1.0, 0.001F));
        this.goalSelector.add(5, new net.minecraft.entity.ai.goal.LookAroundGoal(this));
        this.goalSelector.add(6, new IdleShiftGoal(this));
    }

    @Override
    public void onSpawnedBySpell(Args args) {
        var owner = args.owner();
        var spellId = args.spell().getKey().get().getValue();
        var spawn = args.spawnData();

        this.spellId = spellId;
        this.getDataTracker().set(SPELL_ID_TRACKER, this.spellId.toString());
        this.ownerUuid = owner.getUuid();
        this.cachedOwner = owner;
        this.timeToLive = spawn.time_to_live_seconds * 20;
        this.getDataTracker().set(TIME_TO_LIVE_TRACKER, this.timeToLive);

        if (owner instanceof PlayerEntity player) {
            this.playerUuid = player.getUuid();
            this.getDataTracker().set(PLAYER_UUID_TRACKER, this.playerUuid.toString());
            this.copiedHealth = player.getHealth();
            this.getDataTracker().set(COPIED_HEALTH_TRACKER, this.copiedHealth);

            var armor = player.getInventory().armor;
            this.getDataTracker().set(HELMET_TRACKER, armor.get(3).copy());
            this.getDataTracker().set(CHESTPLATE_TRACKER, armor.get(2).copy());
            this.getDataTracker().set(LEGGINGS_TRACKER, armor.get(1).copy());
            this.getDataTracker().set(BOOTS_TRACKER, armor.get(0).copy());

            this.getDataTracker().set(MAIN_HAND_TRACKER, player.getMainHandStack().copy());
            this.getDataTracker().set(OFF_HAND_TRACKER, player.getOffHandStack().copy());
        }
        final ParticleBatch POP_PARTICLES = new ParticleBatch(
                SpellEngineParticles.smoke_medium.id().toString(),
                ParticleBatch.Shape.CIRCLE,
                ParticleBatch.Origin.FEET,
                null,
                20,
                0.18F,
                0.2F,
                0);
        final Identifier LEAVE_SOUND_ID = Identifier.of(ArchersExpansionMod.MOD_ID, "infiltrator_vanish");
        final SoundEvent LEAVE_SOUND = SoundEvent.of(LEAVE_SOUND_ID);
        if (!this.getWorld().isClient()) {
            SoundHelper.playSoundEvent(this.getWorld(), this, LEAVE_SOUND);
            ParticleHelper.sendBatches(this, new ParticleBatch[]{POP_PARTICLES});
        }
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);

        builder.add(SPELL_ID_TRACKER, "");
        builder.add(TIME_TO_LIVE_TRACKER, 0);
        builder.add(COPIED_HEALTH_TRACKER, 20.0F);
        builder.add(PLAYER_UUID_TRACKER, "");

        builder.add(HELMET_TRACKER, ItemStack.EMPTY);
        builder.add(CHESTPLATE_TRACKER, ItemStack.EMPTY);
        builder.add(LEGGINGS_TRACKER, ItemStack.EMPTY);
        builder.add(BOOTS_TRACKER, ItemStack.EMPTY);
        builder.add(MAIN_HAND_TRACKER, ItemStack.EMPTY);
        builder.add(OFF_HAND_TRACKER, ItemStack.EMPTY);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        var rawSpellId = this.getDataTracker().get(SPELL_ID_TRACKER);
        if (rawSpellId != null && !rawSpellId.isEmpty()) {
            this.spellId = Identifier.of(rawSpellId);
        }
        this.timeToLive = this.getDataTracker().get(TIME_TO_LIVE_TRACKER);
        this.copiedHealth = this.getDataTracker().get(COPIED_HEALTH_TRACKER);

        var playerUuidStr = this.getDataTracker().get(PLAYER_UUID_TRACKER);
        if (playerUuidStr != null && !playerUuidStr.isEmpty()) {
            try {
                this.playerUuid = UUID.fromString(playerUuidStr);
            } catch (IllegalArgumentException e) {
            }
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);

        if (nbt.contains("SpellId")) {
            this.spellId = Identifier.of(nbt.getString("SpellId"));
            this.getDataTracker().set(SPELL_ID_TRACKER, this.spellId.toString());
        }
        if (nbt.containsUuid("Owner")) this.ownerUuid = nbt.getUuid("Owner");
        this.timeToLive = nbt.getInt("TimeToLive");
        this.copiedHealth = nbt.getFloat("CopiedHealth");
        this.hasExploded = nbt.getBoolean("HasExploded");

        if (nbt.containsUuid("PlayerUuid")) {
            this.playerUuid = nbt.getUuid("PlayerUuid");
        }

        this.getDataTracker().set(TIME_TO_LIVE_TRACKER, this.timeToLive);
        this.getDataTracker().set(COPIED_HEALTH_TRACKER, this.copiedHealth);
        if (this.playerUuid != null) {
            this.getDataTracker().set(PLAYER_UUID_TRACKER, this.playerUuid.toString());
        }
    }

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);

        if (this.spellId != null) nbt.putString("SpellId", this.spellId.toString());
        if (this.ownerUuid != null) nbt.putUuid("Owner", this.ownerUuid);
        nbt.putInt("TimeToLive", this.timeToLive);
        nbt.putFloat("CopiedHealth", this.copiedHealth);
        nbt.putBoolean("HasExploded", this.hasExploded);

        if (this.playerUuid != null) {
            nbt.putUuid("PlayerUuid", this.playerUuid);
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (this.hasExploded) return;

        if (!this.getWorld().isClient()) {
            var owner = this.getOwner();
            if (owner == null || owner.isRemoved() || !owner.isAlive()) {
                this.discard();
                return;
            }

            if (this.age > this.timeToLive) {
                this.triggerExplosion();
                this.discard();
                return;
            }

            checkEnemyCollision();
        }
    }

    @Override
    public boolean damage(net.minecraft.entity.damage.DamageSource source, float amount) {
        if (source.getAttacker() instanceof LivingEntity attacker) {
            this.lastAttacker = attacker;
        }
        return super.damage(source, amount);
    }

    private void checkEnemyCollision() {
        var owner = this.getOwner();
        if (owner == null) return;

        Box searchBox = this.getBoundingBox().expand(0.5);
        var entities = this.getWorld().getOtherEntities(this, searchBox);

        for (Entity entity : entities) {
            if (!(entity instanceof LivingEntity)) continue;
            if (entity == owner) continue;

            if (entity instanceof AlterEgoEntity) continue;

            if (!isProtected(owner, entity)) {
                triggerExplosion();
                break;
            }
        }
    }

    @Override
    public void onDeath(net.minecraft.entity.damage.DamageSource damageSource) {
        if (!this.hasExploded) {
            var owner = this.getOwner();
            var attacker = this.lastAttacker != null
                    ? this.lastAttacker
                    : (damageSource.getAttacker() instanceof LivingEntity a ? a : null);
            if (owner != null && attacker != null && !isProtected(owner, attacker)) {
                explodeEffects();
            }
        }
        super.onDeath(damageSource);
    }

    @Override
    protected void dropInventory() {
    }

    private void triggerExplosion() {
        if (this.hasExploded) return;
        explodeEffects();
        this.discard();
    }

    private void explodeEffects() {
        if (this.hasExploded) return;
        this.hasExploded = true;

        var owner = this.getOwner();
        if (owner == null) return;

        applyExplosionSpell();

    }

    private void applyExplosionSpell() {
        var owner = this.getOwner();
        if (owner == null) return;
        SpellAreaExplosion.trigger(this, owner, Identifier.of(MOD_ID, "alter_ego_explosion"));
    }

    public LivingEntity getOwner() {
        if (cachedOwner != null && cachedOwner.isAlive()) {
            return cachedOwner;
        }
        if (ownerUuid != null && getWorld() instanceof ServerWorld sw) {
            Entity e = sw.getEntity(ownerUuid);
            if (e instanceof LivingEntity living) {
                cachedOwner = living;
                return living;
            }
        }
        return null;
    }

    public UUID getPlayerUuid() {
        return this.playerUuid;
    }

    public float getCopiedHealth() {
        return this.copiedHealth;
    }
    @Override
    public Iterable<ItemStack> getArmorItems() {
        return java.util.List.of(
            this.getDataTracker().get(BOOTS_TRACKER),
            this.getDataTracker().get(LEGGINGS_TRACKER),
            this.getDataTracker().get(CHESTPLATE_TRACKER),
            this.getDataTracker().get(HELMET_TRACKER)
        );
    }

    @Override
    public ItemStack getEquippedStack(net.minecraft.entity.EquipmentSlot slot) {
        return switch (slot) {
            case HEAD -> this.getDataTracker().get(HELMET_TRACKER);
            case CHEST -> this.getDataTracker().get(CHESTPLATE_TRACKER);
            case LEGS -> this.getDataTracker().get(LEGGINGS_TRACKER);
            case FEET -> this.getDataTracker().get(BOOTS_TRACKER);
            case MAINHAND -> this.getDataTracker().get(MAIN_HAND_TRACKER);
            case OFFHAND -> this.getDataTracker().get(OFF_HAND_TRACKER);
            default -> ItemStack.EMPTY;
        };
    }

    @Override
    public void equipStack(net.minecraft.entity.EquipmentSlot slot, ItemStack stack) {
    }

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    private class ProvokeHostilesGoal extends net.minecraft.entity.ai.goal.Goal {
        private final AlterEgoEntity alterEgo;
        private int updateCountdownTicks;

        public ProvokeHostilesGoal(AlterEgoEntity alterEgo) {
            this.alterEgo = alterEgo;
            this.updateCountdownTicks = 0;
        }

        @Override
        public boolean canStart() {
            return true;
        }

        @Override
        public boolean shouldContinue() {
            return true;
        }

        @Override
        public void start() {
            this.updateCountdownTicks = 0;
        }

        @Override
        public void tick() {
            if (--this.updateCountdownTicks <= 0) {
                this.updateCountdownTicks = 20;

                LivingEntity owner = alterEgo.getOwner();
                if (owner == null) return;

                var nearbyEntities = alterEgo.getWorld().getOtherEntities(alterEgo,
                    alterEgo.getBoundingBox().expand(12.0),
                    entity -> entity instanceof net.minecraft.entity.mob.MobEntity);

                for (Entity entity : nearbyEntities) {
                    if (!(entity instanceof net.minecraft.entity.mob.MobEntity mobEntity)) continue;
                    if (entity == owner) continue;

                    if (!isProtected(owner, entity)) {
                        mobEntity.setTarget(alterEgo);
                    }
                }
            }
        }
    }

    private class FleeFromHostilesGoal extends net.minecraft.entity.ai.goal.Goal {
        private final AlterEgoEntity alterEgo;
        private final double speed;
        private final float minDistance;
        private final float maxDistance;
        private LivingEntity targetEntity;
        private int updateCountdownTicks;

        public FleeFromHostilesGoal(AlterEgoEntity alterEgo, double speed, float minDistance, float maxDistance) {
            this.alterEgo = alterEgo;
            this.speed = speed;
            this.minDistance = minDistance;
            this.maxDistance = maxDistance;
            this.setControls(java.util.EnumSet.of(Control.MOVE));
        }

        @Override
        public boolean canStart() {
            LivingEntity owner = alterEgo.getOwner();
            if (owner == null) return false;

            targetEntity = findNearestHostile();
            if (targetEntity == null) return false;

            return alterEgo.squaredDistanceTo(targetEntity) < (maxDistance * maxDistance);
        }

        @Override
        public boolean shouldContinue() {
            if (targetEntity == null || !targetEntity.isAlive()) return false;
            return alterEgo.squaredDistanceTo(targetEntity) < (minDistance * minDistance);
        }

        @Override
        public void start() {
            this.updateCountdownTicks = 0;
        }

        @Override
        public void stop() {
            alterEgo.getNavigation().stop();
            targetEntity = null;
        }

        @Override
        public void tick() {
            if (targetEntity == null) return;

            if (--this.updateCountdownTicks <= 0) {
                this.updateCountdownTicks = 20;

                if (alterEgo.getNavigation().isIdle()) {
                    Vec3d fleeDirection = alterEgo.getPos().subtract(targetEntity.getPos()).normalize();
                    Vec3d fleeTarget = alterEgo.getPos().add(fleeDirection.multiply(8.0));
                    alterEgo.getNavigation().startMovingTo(fleeTarget.x, fleeTarget.y, fleeTarget.z, this.speed);
                }
            }
        }

        private LivingEntity findNearestHostile() {
            LivingEntity owner = alterEgo.getOwner();
            if (owner == null) return null;

            var nearbyEntities = alterEgo.getWorld().getOtherEntities(alterEgo,
                alterEgo.getBoundingBox().expand(maxDistance),
                entity -> entity instanceof LivingEntity);

            LivingEntity nearest = null;
            double nearestDistance = Double.MAX_VALUE;

            for (Entity entity : nearbyEntities) {
                if (!(entity instanceof LivingEntity livingEntity)) continue;
                if (entity == owner || entity == alterEgo) continue;

                if (!isProtected(owner, entity)) {
                    double distance = alterEgo.squaredDistanceTo(entity);
                    if (distance < nearestDistance) {
                        nearest = livingEntity;
                        nearestDistance = distance;
                    }
                }
            }

            return nearest;
        }
    }
    private class IdleShiftGoal extends net.minecraft.entity.ai.goal.Goal {
        private final AlterEgoEntity alterEgo;
        private int cooldownTicks;

        public IdleShiftGoal(AlterEgoEntity alterEgo) {
            this.alterEgo = alterEgo;
            this.setControls(java.util.EnumSet.of(Control.MOVE));
            this.cooldownTicks = pickCooldown();
        }

        private int pickCooldown() {
            return 60 + alterEgo.random.nextInt(140);
        }

        @Override
        public boolean canStart() {
            if (--this.cooldownTicks > 0) return false;
            return alterEgo.getNavigation().isIdle();
        }

        @Override
        public boolean shouldContinue() {
            return !alterEgo.getNavigation().isIdle();
        }

        @Override
        public void start() {
            double angle = alterEgo.random.nextDouble() * (Math.PI * 2);
            double distance = 1.5 + alterEgo.random.nextDouble() * 2.0;
            double targetX = alterEgo.getX() + Math.cos(angle) * distance;
            double targetZ = alterEgo.getZ() + Math.sin(angle) * distance;
            alterEgo.getNavigation().startMovingTo(targetX, alterEgo.getY(), targetZ, 0.6);
        }

        @Override
        public void stop() {
            this.cooldownTicks = pickCooldown();
        }
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

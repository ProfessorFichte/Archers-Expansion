package com.archers_expansion.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.entity.SpellEntity;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.fx.ParticleHelper;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.target.EntityRelations;
import net.spell_engine.utils.TargetHelper;
import net.spell_power.api.SpellPower;

import java.util.List;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ExplosiveBarrelEntity extends Entity implements SpellEntity.Spawned {
    public static EntityType<ExplosiveBarrelEntity> ENTITY_TYPE;

    private static final double BARREL_DETECTION_RADIUS = 16.0;
    private static final int CHECK_INTERVAL = 10;
    private static final TrackedData<String> SPELL_ID_TRACKER = DataTracker.registerData(ExplosiveBarrelEntity.class, TrackedDataHandlerRegistry.STRING);
    private static final TrackedData<Integer> OWNER_ID_TRACKER = DataTracker.registerData(ExplosiveBarrelEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Integer> TIME_TO_LIVE_TRACKER = DataTracker.registerData(ExplosiveBarrelEntity.class, TrackedDataHandlerRegistry.INTEGER);
    private static final TrackedData<Float> HEALTH_TRACKER = DataTracker.registerData(ExplosiveBarrelEntity.class, TrackedDataHandlerRegistry.FLOAT);
    private static final TrackedData<Integer> EXPLOSION_COUNTDOWN_TRACKER = DataTracker.registerData(ExplosiveBarrelEntity.class, TrackedDataHandlerRegistry.INTEGER);

    private Identifier spellId;
    private int ownerId;
    private int timeToLive;
    private LivingEntity cachedOwner = null;
    private boolean hasExploded = false;
    private float health = 2.0F;
    private int explosionCountdown = -1;

    public ExplosiveBarrelEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    public void onSpawnedBySpell(Args args) {
        var owner = args.owner();
        var spellId = args.spell().getKey().get().getValue();
        var spawn = args.spawnData();

        this.spellId = spellId;
        this.getDataTracker().set(SPELL_ID_TRACKER, this.spellId.toString());
        this.ownerId = owner.getId();
        this.getDataTracker().set(OWNER_ID_TRACKER, this.ownerId);
        this.timeToLive = spawn.time_to_live_seconds * 20;
        this.getDataTracker().set(TIME_TO_LIVE_TRACKER, this.timeToLive);
        this.health = 2.0F;
        this.getDataTracker().set(HEALTH_TRACKER, this.health);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        builder.add(SPELL_ID_TRACKER, "");
        builder.add(OWNER_ID_TRACKER, 0);
        builder.add(TIME_TO_LIVE_TRACKER, 0);
        builder.add(HEALTH_TRACKER, 2.0F);
        builder.add(EXPLOSION_COUNTDOWN_TRACKER, -1);
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        var rawSpellId = this.getDataTracker().get(SPELL_ID_TRACKER);
        if (rawSpellId != null && !rawSpellId.isEmpty()) {
            this.spellId = Identifier.of(rawSpellId);
        }
        this.timeToLive = this.getDataTracker().get(TIME_TO_LIVE_TRACKER);
        this.health = this.getDataTracker().get(HEALTH_TRACKER);
        this.explosionCountdown = this.getDataTracker().get(EXPLOSION_COUNTDOWN_TRACKER);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        this.spellId = Identifier.of(nbt.getString("SpellId"));
        this.ownerId = nbt.getInt("OwnerId");
        this.timeToLive = nbt.getInt("TimeToLive");
        this.hasExploded = nbt.getBoolean("HasExploded");
        this.health = nbt.getFloat("Health");
        this.explosionCountdown = nbt.getInt("ExplosionCountdown");

        this.getDataTracker().set(SPELL_ID_TRACKER, this.spellId.toString());
        this.getDataTracker().set(OWNER_ID_TRACKER, this.ownerId);
        this.getDataTracker().set(TIME_TO_LIVE_TRACKER, this.timeToLive);
        this.getDataTracker().set(HEALTH_TRACKER, this.health);
        this.getDataTracker().set(EXPLOSION_COUNTDOWN_TRACKER, this.explosionCountdown);
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putString("SpellId", this.spellId.toString());
        nbt.putInt("OwnerId", this.ownerId);
        nbt.putInt("TimeToLive", this.timeToLive);
        nbt.putBoolean("HasExploded", this.hasExploded);
        nbt.putFloat("Health", this.health);
        nbt.putInt("ExplosionCountdown", this.explosionCountdown);
    }

    @Override
    public void tick() {
        super.tick();

        // Check if owner is dead or removed
        var owner = this.getOwner();
        if (owner == null || owner.isRemoved() || !owner.isAlive()) {
            this.discard();
            return;
        }

        // Check if entity should despawn
        if (this.age > this.timeToLive && !this.hasExploded) {
            this.discard();
            return;
        }

        if (this.hasExploded) {
            return;
        }

        var world = this.getWorld();

        if (!world.isClient()) {
            if (this.explosionCountdown >= 0) {
                if (this.explosionCountdown == 0) {
                    triggerExplosion();
                    return;
                }
                this.explosionCountdown--;
                this.getDataTracker().set(EXPLOSION_COUNTDOWN_TRACKER, this.explosionCountdown);
            }

            if (this.age % CHECK_INTERVAL == 0) {
                checkEnemyCollision();
            }
        } else {
            if (this.age % CHECK_INTERVAL == 0) {
                renderParticleLinesToNearbyBarrels();
            }
        }
    }

    private void checkEnemyCollision() {
        var owner = this.getOwner();
        if (owner == null) return;

        Box searchBox = this.getBoundingBox().expand(0.5);
        var entities = this.getWorld().getOtherEntities(this, searchBox);

        for (Entity entity : entities) {
            if (!(entity instanceof LivingEntity livingEntity)) continue;

            if (entity == owner) continue;

            if (entity instanceof AlterEgoEntity) continue;

            if (!isProtected(owner, entity)) {
                triggerExplosion();
                break;
            }
        }
    }

    private void renderParticleLinesToNearbyBarrels() {
        List<ExplosiveBarrelEntity> nearbyBarrels = findNearbyBarrels();

        ExplosiveBarrelEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (ExplosiveBarrelEntity barrel : nearbyBarrels) {
            if (barrel == this || barrel.hasExploded) continue;

            double distance = this.squaredDistanceTo(barrel);
            if (distance < nearestDistance) {
                nearest = barrel;
                nearestDistance = distance;
            }
        }

        if (nearest != null) {
            boolean isIgnited = this.explosionCountdown >= 0;
            drawParticleLine(this.getPos(), nearest.getPos(), isIgnited);
        }
    }

    private void drawParticleLine(Vec3d from, Vec3d to, boolean isIgnited) {
        World world = this.getWorld();
        Vec3d direction = to.subtract(from);
        double distance = direction.length();
        Vec3d step = direction.normalize().multiply(0.5);

        int steps = (int) (distance / 0.5);

        for (int i = 0; i < steps; i++) {
            Vec3d particlePos = from.add(step.multiply(i));

            world.addParticle(
                isIgnited ? ParticleTypes.FLAME : ParticleTypes.SMOKE,
                particlePos.x,
                particlePos.y + 0.1,
                particlePos.z,
                0, 0.01, 0
            );
        }
    }

    private List<ExplosiveBarrelEntity> findNearbyBarrels() {
        Box searchBox = Box.of(this.getPos(),
            BARREL_DETECTION_RADIUS * 2,
            BARREL_DETECTION_RADIUS * 2,
            BARREL_DETECTION_RADIUS * 2);

        return this.getWorld().getEntitiesByClass(
            ExplosiveBarrelEntity.class,
            searchBox,
            barrel -> barrel != this && !barrel.hasExploded
        );
    }

    @Override
    public boolean damage(DamageSource source, float amount) {
        if (this.hasExploded) return false;

        Entity attacker = source.getAttacker();
        var owner = this.getOwner();

        if (owner == null) {
            return false;
        }

        if (attacker != owner) {
            return false;
        }

        this.health -= amount;
        this.getDataTracker().set(HEALTH_TRACKER, this.health);

        if (this.health <= 0) {
            triggerExplosion();
            return true;
        }

        return true;
    }

    private void triggerExplosion() {
        if (this.hasExploded) return;

        this.hasExploded = true;

        var owner = this.getOwner();
        if (owner == null) {
            this.discard();
            return;
        }

        applyExplosionSpell();

        List<ExplosiveBarrelEntity> nearbyBarrels = findNearbyBarrels();

        ExplosiveBarrelEntity nearest = null;
        double nearestDistance = Double.MAX_VALUE;

        for (ExplosiveBarrelEntity barrel : nearbyBarrels) {
            if (!barrel.hasExploded && barrel.explosionCountdown < 0) {
                double distance = this.squaredDistanceTo(barrel);
                if (distance < nearestDistance) {
                    nearest = barrel;
                    nearestDistance = distance;
                }
            }
        }

        if (nearest != null) {
            double distance = Math.sqrt(nearestDistance);
            int delay = (int) Math.round(distance * 3.0);
            nearest.scheduleExplosion(delay);
        }
        this.discard();
    }

    private void scheduleExplosion(int delay) {
        this.explosionCountdown = delay;
        this.getDataTracker().set(EXPLOSION_COUNTDOWN_TRACKER, this.explosionCountdown);
    }

    private void applyExplosionSpell() {
        var owner = this.getOwner();
        if (owner == null) return;
        RegistryEntry<Spell> spellExplosion = SpellRegistry.from(
                owner.getWorld()).getEntry(Identifier.of(MOD_ID, "explosive_barrel_explosion")).get();
        ParticleHelper.sendBatches(this, spellExplosion.value().release.particles);
        ParticleHelper.sendBatches(this, spellExplosion.value().release.particles_scaled_with_ranged);
        for(Entity targetEntity : TargetHelper.targetsFromArea(this, spellExplosion.value().range, spellExplosion.value().target.area, e -> e != this)) {
            SpellHelper.performImpacts(owner.getWorld(), owner, targetEntity, owner, spellExplosion,
                    spellExplosion.value().impacts, new SpellHelper.ImpactContext().power(SpellPower.getSpellPower(spellExplosion.value().school, owner)).position(this.getPos()));
            ParticleHelper.sendBatches(targetEntity, spellExplosion.value().impacts.get(0).particles);
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

    @Override
    public boolean isCollidable() {
        return true;
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public boolean canHit() {
        return !this.hasExploded;
    }

    @Override
    public boolean isAttackable() {
        return !this.hasExploded;
    }

    public float getHealth() {
        return this.health;
    }

    public float getMaxHealth() {
        return 2.0F;
    }
}

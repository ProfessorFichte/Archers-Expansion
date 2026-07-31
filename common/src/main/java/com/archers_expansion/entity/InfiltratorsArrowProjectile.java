package com.archers_expansion.entity;

import com.archers_expansion.effect.ArchersExpansionEffects;
import com.google.gson.Gson;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraft.world.event.GameEvent;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.internals.SpellHelper;
import org.jetbrains.annotations.Nullable;

public class InfiltratorsArrowProjectile extends ArrowEntity {
    public static EntityType<InfiltratorsArrowProjectile> ENTITY_TYPE;

    public static final float DEFAULT_MAX_RANGE = 14.0F;

    private float maxRange = DEFAULT_MAX_RANGE;
    private RegistryEntry<Spell> spellEntry;
    private SpellHelper.ImpactContext context;
    private Vec3d spawnPos;
    private final Gson gson = new Gson();

    private static final TrackedData<String> TRACKER_SPELL_ID =
            DataTracker.registerData(InfiltratorsArrowProjectile.class, TrackedDataHandlerRegistry.STRING);

    public InfiltratorsArrowProjectile(EntityType<? extends ArrowEntity> type, World world) {
        super(type, world);
    }

    public InfiltratorsArrowProjectile(World world, LivingEntity owner, RegistryEntry<Spell> spellEntry, SpellHelper.ImpactContext context, float maxRange) {
        super(ENTITY_TYPE, world);
        this.setOwner(owner);
        this.spellEntry = spellEntry;
        this.context = context;
        this.maxRange = maxRange;
        this.setDamage(0.0);
        this.pickupType = PersistentProjectileEntity.PickupPermission.DISALLOWED;
        this.getDataTracker().set(TRACKER_SPELL_ID, spellEntry.getKey().get().getValue().toString());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(TRACKER_SPELL_ID, "");
    }

    @Override
    public void onTrackedDataSet(TrackedData<?> data) {
        super.onTrackedDataSet(data);
        if (this.getWorld().isClient && data.equals(TRACKER_SPELL_ID)) {
            var spellId = this.getDataTracker().get(TRACKER_SPELL_ID);
            if (spellId != null && !spellId.isEmpty()) {
                this.spellEntry = SpellRegistry.from(this.getWorld()).getEntry(Identifier.of(spellId)).orElse(null);
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (spawnPos == null) spawnPos = getPos();

        if (!getWorld().isClient && getPos().distanceTo(spawnPos) >= maxRange) {
            discard();
        }
    }

    @Override
    protected void onEntityHit(EntityHitResult hitResult) {
        if (getWorld().isClient) return;

        var target = hitResult.getEntity();
        if (getOwner() instanceof LivingEntity caster && spellEntry != null) {
            var hitPosition = hitResult.getPos();
            var impactContext = context != null ? context : new SpellHelper.ImpactContext();
            SpellHelper.projectileImpact(caster, this, target, spellEntry, impactContext.position(hitPosition));
            teleportOwner(caster, hitPosition);
        }
        discard();
    }

    @Override
    protected void onBlockHit(BlockHitResult hitResult) {
        if (getWorld().isClient) return;

        if (getOwner() instanceof LivingEntity caster && spellEntry != null) {
            var hitPosition = hitResult.getPos();
            var impactContext = context != null ? context : new SpellHelper.ImpactContext();
            SpellHelper.projectileImpact(caster, this, null, spellEntry, impactContext.position(hitPosition));
            teleportOwner(caster, hitPosition);
        }
        discard();
    }

    private void teleportOwner(LivingEntity owner, Vec3d pos) {
        if (owner instanceof ServerPlayerEntity player && getWorld() instanceof ServerWorld serverWorld) {
            player.teleport(serverWorld, pos.x, pos.y, pos.z, player.getYaw(), player.getPitch());
        } else {
            owner.requestTeleport(pos.x, pos.y, pos.z);
        }
        owner.setVelocity(Vec3d.ZERO);
        owner.fallDistance = 0.0F;
        getWorld().emitGameEvent(GameEvent.TELEPORT, pos, GameEvent.Emitter.of(owner));
        owner.addStatusEffect(new StatusEffectInstance(ArchersExpansionEffects.INFILTRATORS_VANISH.entry, 100, 0, false, false, true));
    }

    private static final String NBT_SPELL_ID = "SpellId";
    private static final String NBT_IMPACT_CONTEXT = "ImpactContext";
    private static final String NBT_MAX_RANGE = "MaxRange";

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.spellEntry != null) {
            nbt.putString(NBT_SPELL_ID, this.spellEntry.getKey().get().getValue().toString());
        }
        if (this.context != null) {
            nbt.putString(NBT_IMPACT_CONTEXT, gson.toJson(this.context));
        }
        nbt.putFloat(NBT_MAX_RANGE, this.maxRange);
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains(NBT_MAX_RANGE, NbtElement.FLOAT_TYPE)) {
            this.maxRange = nbt.getFloat(NBT_MAX_RANGE);
        }
        if (nbt.contains(NBT_SPELL_ID, NbtElement.STRING_TYPE)) {
            try {
                var spellId = Identifier.of(nbt.getString(NBT_SPELL_ID));
                this.spellEntry = SpellRegistry.from(this.getWorld()).getEntry(spellId).orElse(null);
                if (this.spellEntry != null) {
                    this.getDataTracker().set(TRACKER_SPELL_ID, spellId.toString());
                }
            } catch (Exception e) {
                System.err.println("InfiltratorsArrowProjectile - Failed to read spell ID from NBT: " + e.getMessage());
            }
        }
        if (nbt.contains(NBT_IMPACT_CONTEXT, NbtElement.STRING_TYPE)) {
            try {
                this.context = gson.fromJson(nbt.getString(NBT_IMPACT_CONTEXT), SpellHelper.ImpactContext.class);
            } catch (Exception e) {
                System.err.println("InfiltratorsArrowProjectile - Failed to read impact context from NBT: " + e.getMessage());
            }
        }
    }

    @Nullable
    public RegistryEntry<Spell> getSpellEntry() {
        return spellEntry;
    }
}

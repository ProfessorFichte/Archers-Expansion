package com.archers_expansion.entity;

import com.google.gson.Gson;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.ProjectileUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.client.render.FlyingSpellEntity;
import net.spell_engine.internals.SpellHelper;
import org.jetbrains.annotations.Nullable;

public class PoisonFlaskProjectile extends ProjectileEntity implements FlyingSpellEntity {
    public static EntityType<PoisonFlaskProjectile> ENTITY_TYPE;

    private static final float GRAVITY = 0.05F;
    private static final float DRAG = 0.99F;
    private static final int MAX_AGE = 600;

    private RegistryEntry<Spell> spellEntry;
    private SpellHelper.ImpactContext context;
    private final Gson gson = new Gson();

    private static final TrackedData<String> TRACKER_SPELL_ID =
            DataTracker.registerData(PoisonFlaskProjectile.class, TrackedDataHandlerRegistry.STRING);

    public PoisonFlaskProjectile(EntityType<? extends ProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    public PoisonFlaskProjectile(World world, LivingEntity owner, RegistryEntry<Spell> spellEntry, SpellHelper.ImpactContext context) {
        super(ENTITY_TYPE, world);
        this.setOwner(owner);
        this.spellEntry = spellEntry;
        this.context = context;
        this.getDataTracker().set(TRACKER_SPELL_ID, spellEntry.getKey().get().getValue().toString());
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
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

        if (!this.getWorld().isClient && this.age > MAX_AGE) {
            this.kill();
            return;
        }

        // Collision detection
        HitResult hitResult = ProjectileUtil.getCollision(this, this::canHit);
        if (hitResult.getType() != HitResult.Type.MISS) {
            this.onCollision(hitResult);
        }

        this.checkBlockCollision();

        // Movement with gravity
        Vec3d velocity = this.getVelocity();
        double x = this.getX() + velocity.x;
        double y = this.getY() + velocity.y;
        double z = this.getZ() + velocity.z;

        ProjectileUtil.setRotationFromVelocity(this, 0.2F);

        float drag = DRAG;
        if (this.isTouchingWater()) {
            drag = 0.8F;
        }

        this.setVelocity(velocity.x * drag, (velocity.y * drag) - GRAVITY, velocity.z * drag);
        this.setPosition(x, y, z);
    }

    @Override
    protected void onEntityHit(EntityHitResult entityHitResult) {
        if (this.getWorld().isClient) return;

        var target = entityHitResult.getEntity();
        if (target != null && this.getOwner() instanceof LivingEntity caster && this.spellEntry != null) {
            var hitPosition = entityHitResult.getPos();
            var impactContext = this.context != null ? this.context : new SpellHelper.ImpactContext();
            SpellHelper.projectileImpact(caster, this, target, this.spellEntry, impactContext.position(hitPosition));
        }
        this.kill();
    }

    @Override
    protected void onBlockHit(BlockHitResult blockHitResult) {
        super.onBlockHit(blockHitResult);
        if (this.getWorld().isClient) return;

        if (this.getOwner() instanceof LivingEntity caster && this.spellEntry != null) {
            var hitPosition = blockHitResult.getPos();
            var impactContext = this.context != null ? this.context : new SpellHelper.ImpactContext();
            SpellHelper.projectileImpact(caster, this, null, this.spellEntry, impactContext.position(hitPosition));
        }
        this.kill();
    }

    // FlyingSpellEntity implementation
    @Override
    public Spell.ProjectileModel renderData() {
        return null;
    }

    @Override
    public ItemStack getStack() {
        return ItemStack.EMPTY;
    }

    // NBT persistence
    private static final String NBT_SPELL_ID = "SpellId";
    private static final String NBT_IMPACT_CONTEXT = "ImpactContext";

    @Override
    public void writeCustomDataToNbt(NbtCompound nbt) {
        super.writeCustomDataToNbt(nbt);
        if (this.spellEntry != null) {
            nbt.putString(NBT_SPELL_ID, this.spellEntry.getKey().get().getValue().toString());
        }
        if (this.context != null) {
            nbt.putString(NBT_IMPACT_CONTEXT, gson.toJson(this.context));
        }
    }

    @Override
    public void readCustomDataFromNbt(NbtCompound nbt) {
        super.readCustomDataFromNbt(nbt);
        if (nbt.contains(NBT_SPELL_ID, NbtElement.STRING_TYPE)) {
            try {
                var spellId = Identifier.of(nbt.getString(NBT_SPELL_ID));
                this.spellEntry = SpellRegistry.from(this.getWorld()).getEntry(spellId).orElse(null);
                if (this.spellEntry != null) {
                    this.getDataTracker().set(TRACKER_SPELL_ID, spellId.toString());
                }
            } catch (Exception e) {
                System.err.println("PoisonFlaskProjectile - Failed to read spell ID from NBT: " + e.getMessage());
            }
        }
        if (nbt.contains(NBT_IMPACT_CONTEXT, NbtElement.STRING_TYPE)) {
            try {
                this.context = gson.fromJson(nbt.getString(NBT_IMPACT_CONTEXT), SpellHelper.ImpactContext.class);
            } catch (Exception e) {
                System.err.println("PoisonFlaskProjectile - Failed to read impact context from NBT: " + e.getMessage());
            }
        }
    }

    @Nullable
    public RegistryEntry<Spell> getSpellEntry() {
        return spellEntry;
    }

    public void setVelocity(double x, double y, double z, float speed) {
        Vec3d vec3d = new Vec3d(x, y, z).normalize().multiply(speed);
        this.setVelocity(vec3d);
        double d = vec3d.horizontalLength();
        this.setYaw((float)(MathHelper.atan2(vec3d.x, vec3d.z) * 57.2957763671875));
        this.setPitch((float)(MathHelper.atan2(vec3d.y, d) * 57.2957763671875));
        this.prevYaw = this.getYaw();
        this.prevPitch = this.getPitch();
    }
}

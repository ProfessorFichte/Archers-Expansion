package com.archers_expansion.spell;

import com.archers_expansion.entity.PoisonFlaskProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.event.SpellHandlers;
import net.spell_engine.internals.SpellExecution;
import net.spell_engine.internals.SpellParameters;
import net.spell_engine.internals.delivery.CloudPlacer;
import net.spell_engine.internals.delivery.LaunchGeometry;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_power.api.SpellPower;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class CustomSpellImpacts {
    private static final float DEGREES_TO_RADIANS = 0.017453292F;

    // Loft angle flattens out the longer the shot's range is: a short hop arcs high like a lob, a long charged shot flies flatter, like a real projectile trading arc for distance.
    private static final float ARC_PROJECTILE_STEEP_ANGLE_DEGREES = 48F;
    private static final float ARC_PROJECTILE_FLAT_ANGLE_DEGREES = 18F;
    private static final float ARC_PROJECTILE_ANGLE_REF_MIN_RANGE = 8F;
    private static final float ARC_PROJECTILE_ANGLE_REF_MAX_RANGE = 36F;
    private static final float ARC_PROJECTILE_MIN_SPEED = 0.1F;
    private static final float ARC_PROJECTILE_MAX_SPEED = 6.0F;
    private static final int ARC_PROJECTILE_SIMULATION_TICKS = 400;
    private static final int ARC_PROJECTILE_SPEED_SEARCH_ITERATIONS = 30;

    // Loft angle
    static float arcProjectileAngleDegrees(float range) {
        var t = MathHelper.clamp(
                (range - ARC_PROJECTILE_ANGLE_REF_MIN_RANGE)
                        / (ARC_PROJECTILE_ANGLE_REF_MAX_RANGE - ARC_PROJECTILE_ANGLE_REF_MIN_RANGE),
                0F, 1F);
        return MathHelper.lerp(t, ARC_PROJECTILE_STEEP_ANGLE_DEGREES, ARC_PROJECTILE_FLAT_ANGLE_DEGREES);
    }

    // gravity/drag must match the real projectile's own per-tick physics, or the predicted landing spot drifts from where it actually lands.
    static float arcProjectileSimulateLanding(float horizontalSpeed, float verticalSpeed, float gravity, float drag) {
        var vx = horizontalSpeed;
        var vy = verticalSpeed;
        var x = 0F;
        var y = 0F;
        for (int tick = 0; tick < ARC_PROJECTILE_SIMULATION_TICKS; tick++) {
            x += vx;
            y += vy;
            vx *= drag;
            vy = vy * drag - gravity;
            if (tick > 0 && y <= 0F) {
                break;
            }
        }
        return x;
    }

    // Binary-searches the launch speed (at the given fixed angle) that lands a real projectile at approximately `range` blocks away.
    static float arcProjectileSpeedForRange(float range, float angleDegrees, float gravity, float drag) {
        var radians = angleDegrees * DEGREES_TO_RADIANS;
        var cos = MathHelper.cos(radians);
        var sin = MathHelper.sin(radians);
        var low = ARC_PROJECTILE_MIN_SPEED;
        var high = ARC_PROJECTILE_MAX_SPEED;
        var mid = high;
        for (int i = 0; i < ARC_PROJECTILE_SPEED_SEARCH_ITERATIONS; i++) {
            mid = (low + high) / 2F;
            var landed = arcProjectileSimulateLanding(mid * cos, mid * sin, gravity, drag);
            if (landed < range) {
                low = mid;
            } else {
                high = mid;
            }
        }
        return mid;
    }

    public static void registerCustomImpacts(){
        SpellHandlers.registerCustomImpact(
                Identifier.of(MOD_ID, "venom_cask_cloud_impact"),
                (spell, powerResult, caster, target, context) ->
                        new SpellHandlers.ImpactResult(placeVenomCloud(caster, target, context.position(), context), false)
        );
    }

    public static boolean placeVenomCloud(LivingEntity caster, Entity target, Vec3d position, SpellExecution.ImpactContext context) {
        var cloudEntryOptional = SpellRegistry.from(caster.getWorld())
                .getEntry(Identifier.of(MOD_ID, "venom_cask_cloud"));
        if (cloudEntryOptional.isEmpty()) return false;
        CloudPlacer.placeCloud(caster.getWorld(), caster, target, position, cloudEntryOptional.get(), context);
        return true;
    }

    // Must match PoisonFlaskProjectile's own GRAVITY/DRAG constants.
    private static final float VENOM_FLASK_GRAVITY = 0.04F;
    private static final float VENOM_FLASK_DRAG = 0.99F;

    public static void registerCustomDeliveries() {
        SpellHandlers.registerCustomDelivery(
                Identifier.of(MOD_ID, "venom_flask"),
                (world, spellEntry, caster, targets, context, targetLocation) -> {
                    if (world.isClient) return false;

                    var spell = spellEntry.value();
                    var impactContext = context;
                    if (impactContext.power() == null) {
                        impactContext = impactContext.power(SpellPower.getSpellPower(spell.school, caster));
                    }

                    var effectiveRange = SpellParameters.getRangeCurved(caster, spellEntry, impactContext.charge());
                    var launchPoint = LaunchGeometry.launchPoint(caster);

                    // Pitch is fully computed from the target range (a lobbed, mortar-like arc) rather than the caster's look pitch, so the flask reliably lands at `range` regardless of aim.
                    var angleDegrees = arcProjectileAngleDegrees(effectiveRange);
                    var speed = arcProjectileSpeedForRange(effectiveRange, angleDegrees, VENOM_FLASK_GRAVITY, VENOM_FLASK_DRAG);
                    var angleRadians = angleDegrees * DEGREES_TO_RADIANS;
                    var horizontalSpeed = speed * MathHelper.cos(angleRadians);
                    var verticalSpeed = speed * MathHelper.sin(angleRadians);

                    var yaw = caster.getYaw() * DEGREES_TO_RADIANS;
                    var vx = -MathHelper.sin(yaw) * horizontalSpeed;
                    var vy = verticalSpeed;
                    var vz = MathHelper.cos(yaw) * horizontalSpeed;

                    var projectile = new PoisonFlaskProjectile(world, caster, spellEntry, impactContext);
                    projectile.setPosition(launchPoint);
                    projectile.setVelocity(vx, vy, vz, speed);

                    world.spawnEntity(projectile);
                    return true;
                }
        );
    }
}

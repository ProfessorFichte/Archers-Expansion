package com.archers_expansion.spell;

import com.archers_expansion.entity.InfiltratorsArrowProjectile;
import com.archers_expansion.entity.PoisonFlaskProjectile;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.spell_engine.api.spell.event.SpellHandlers;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_power.api.SpellPower;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class CustomSpellImpacts {
    private static final float THROW_ROLL_DEGREES = -20.0F;
    private static final float THROW_SPEED = 1.3F;
    private static final float DEGREES_TO_RADIANS = 0.017453292F;

    private static final float INFILTRATORS_ARROW_GRAVITY = 0.05F;
    private static final float INFILTRATORS_ARROW_DRAG = 0.99F;
    // Loft angle flattens out the longer the shot's range is: a short hop arcs high like a lob, a long charged shot flies flatter, like a real projectile trading arc for distance.
    private static final float INFILTRATORS_ARROW_STEEP_ANGLE_DEGREES = 48F;
    private static final float INFILTRATORS_ARROW_FLAT_ANGLE_DEGREES = 18F;
    private static final float INFILTRATORS_ARROW_ANGLE_REF_MIN_RANGE = 8F;
    private static final float INFILTRATORS_ARROW_ANGLE_REF_MAX_RANGE = 36F;
    private static final float INFILTRATORS_ARROW_MIN_SPEED = 0.1F;
    private static final float INFILTRATORS_ARROW_MAX_SPEED = 6.0F;
    private static final int INFILTRATORS_ARROW_SIMULATION_TICKS = 400;
    private static final int INFILTRATORS_ARROW_SPEED_SEARCH_ITERATIONS = 30;

    private static final float INFILTRATORS_ARROW_RANGE_BUFFER = 4F;

    // Loft angle
    private static float infiltratorsArrowAngleDegrees(float range) {
        var t = MathHelper.clamp(
                (range - INFILTRATORS_ARROW_ANGLE_REF_MIN_RANGE)
                        / (INFILTRATORS_ARROW_ANGLE_REF_MAX_RANGE - INFILTRATORS_ARROW_ANGLE_REF_MIN_RANGE),
                0F, 1F);
        return MathHelper.lerp(t, INFILTRATORS_ARROW_STEEP_ANGLE_DEGREES, INFILTRATORS_ARROW_FLAT_ANGLE_DEGREES);
    }

    private static float infiltratorsArrowSimulateLanding(float horizontalSpeed, float verticalSpeed) {
        var vx = horizontalSpeed;
        var vy = verticalSpeed;
        var x = 0F;
        var y = 0F;
        for (int tick = 0; tick < INFILTRATORS_ARROW_SIMULATION_TICKS; tick++) {
            x += vx;
            y += vy;
            vx *= INFILTRATORS_ARROW_DRAG;
            vy = vy * INFILTRATORS_ARROW_DRAG - INFILTRATORS_ARROW_GRAVITY;
            if (tick > 0 && y <= 0F) {
                break;
            }
        }
        return x;
    }

    // Binary-searches the launch speed (at the given fixed angle) that lands a real arrow at approximately `range` blocks away.
    private static float infiltratorsArrowSpeedForRange(float range, float angleDegrees) {
        var radians = angleDegrees * DEGREES_TO_RADIANS;
        var cos = MathHelper.cos(radians);
        var sin = MathHelper.sin(radians);
        var low = INFILTRATORS_ARROW_MIN_SPEED;
        var high = INFILTRATORS_ARROW_MAX_SPEED;
        var mid = high;
        for (int i = 0; i < INFILTRATORS_ARROW_SPEED_SEARCH_ITERATIONS; i++) {
            mid = (low + high) / 2F;
            var landed = infiltratorsArrowSimulateLanding(mid * cos, mid * sin);
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

    public static boolean placeVenomCloud(LivingEntity caster, Entity target, Vec3d position, SpellHelper.ImpactContext context) {
        var cloudEntryOptional = SpellRegistry.from(caster.getWorld())
                .getEntry(Identifier.of(MOD_ID, "venom_cask_cloud"));
        if (cloudEntryOptional.isEmpty()) return false;
        SpellHelper.placeCloud(caster.getWorld(), caster, target, position, cloudEntryOptional.get(), context);
        return true;
    }

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

                    var launchPoint = SpellHelper.launchPoint(caster);
                    var projectile = new PoisonFlaskProjectile(world, caster, spellEntry, impactContext);
                    projectile.setPosition(launchPoint);

                    var pitch = caster.getPitch();
                    var yaw = caster.getYaw();
                    var vx = -MathHelper.sin(yaw * DEGREES_TO_RADIANS) * MathHelper.cos(pitch * DEGREES_TO_RADIANS);
                    var vy = -MathHelper.sin((pitch + THROW_ROLL_DEGREES) * DEGREES_TO_RADIANS);
                    var vz = MathHelper.cos(yaw * DEGREES_TO_RADIANS) * MathHelper.cos(pitch * DEGREES_TO_RADIANS);
                    projectile.setVelocity(vx, vy, vz, THROW_SPEED);

                    world.spawnEntity(projectile);
                    return true;
                }
        );

        SpellHandlers.registerCustomDelivery(
                Identifier.of(MOD_ID, "infiltrators_arrow"),
                (world, spellEntry, caster, targets, context, targetLocation) -> {
                    if (world.isClient) return false;

                    var spell = spellEntry.value();
                    var impactContext = context;
                    if (impactContext.power() == null) {
                        impactContext = impactContext.power(SpellPower.getSpellPower(spell.school, caster));
                    }

                    var effectiveRange = SpellHelper.getRange(caster, spellEntry, impactContext.chargeModifier());
                    var launchPoint = SpellHelper.launchPoint(caster);

                    // Pitch is fully computed from the target range (a lobbed, mortar-like arc) rather than the caster's look pitch, so the shot reliably lands at `range` regardless of aim.
                    var angleDegrees = infiltratorsArrowAngleDegrees(effectiveRange);
                    var speed = infiltratorsArrowSpeedForRange(effectiveRange, angleDegrees);
                    var angleRadians = angleDegrees * DEGREES_TO_RADIANS;
                    var horizontalSpeed = speed * MathHelper.cos(angleRadians);
                    var verticalSpeed = speed * MathHelper.sin(angleRadians);
                    var landingDistance = infiltratorsArrowSimulateLanding(horizontalSpeed, verticalSpeed);

                    var yaw = caster.getYaw() * DEGREES_TO_RADIANS;
                    var vx = -MathHelper.sin(yaw) * horizontalSpeed;
                    var vy = verticalSpeed;
                    var vz = MathHelper.cos(yaw) * horizontalSpeed;

                    var projectile = new InfiltratorsArrowProjectile(world, caster, spellEntry, impactContext,
                            landingDistance + INFILTRATORS_ARROW_RANGE_BUFFER);
                    projectile.setPosition(launchPoint);
                    projectile.setVelocity(vx, vy, vz);

                    world.spawnEntity(projectile);
                    return true;
                }
        );
    }
}

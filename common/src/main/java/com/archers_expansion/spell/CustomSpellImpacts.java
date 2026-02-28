package com.archers_expansion.spell;

import com.archers_expansion.entity.PoisonFlaskProjectile;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.spell_engine.api.spell.event.SpellHandlers;
import net.spell_engine.internals.SpellHelper;
import net.spell_power.api.SpellPower;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class CustomSpellImpacts {
    public static void registerCustomImpacts(){
        /*SpellHandlers.registerCustomImpact(
                Identifier.of(MOD_ID, "alter_ego_summon"),
                new AlterEgoSummon()
        );
         */
    }

    public static void registerCustomDeliveries() {
        SpellHandlers.registerCustomDelivery(
                Identifier.of(MOD_ID, "venom_flask"),
                (world, spellEntry, caster, targets, context, targetLocation) -> {
                    if (world.isClient) return false;

                    var spell = spellEntry.value();
                    // Ensure power is set in context
                    var impactContext = context;
                    if (impactContext.power() == null) {
                        impactContext = impactContext.power(SpellPower.getSpellPower(spell.school, caster));
                    }

                    var launchPoint = SpellHelper.launchPoint(caster);
                    var projectile = new PoisonFlaskProjectile(world, caster, spellEntry, impactContext);
                    projectile.setPosition(launchPoint);

                    var look = caster.getRotationVector().normalize();
                    projectile.setVelocity(look.x, look.y, look.z, 1.0F);

                    world.spawnEntity(projectile);
                    return true;
                }
        );
    }
}

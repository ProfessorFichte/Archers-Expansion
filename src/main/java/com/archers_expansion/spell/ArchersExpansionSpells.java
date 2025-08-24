package com.archers_expansion.spell;

import com.archers_expansion.custom.ArcherySchools;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.ParticleBatch;
import net.spell_engine.api.spell.fx.Sound;
import net.spell_engine.api.util.TriState;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.internals.target.SpellTarget;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ArchersExpansionSpells {
    public record Entry(Identifier id, Spell spell, String title, String description,
                        @Nullable SpellTooltip.DescriptionMutator mutator) {
    }

    public static final List<Entry> entries = new ArrayList<>();

    private static Entry add(Entry entry) {
        entries.add(entry);
        return entry;
    }
    private static Spell modifierSpellBase() {
        var spell = new Spell();
        spell.range = 0;
        spell.tier = 1;

        spell.type = Spell.Type.MODIFIER;

        spell.tooltip = new Spell.Tooltip();
        spell.tooltip.name = new Spell.Tooltip.LineOptions(false, true);
        spell.tooltip.description.color = Formatting.GRAY.asString();
        spell.tooltip.description.show_in_compact = true;
        spell.tooltip.name.show_in_compact = false;
        spell.tooltip.name.show_in_details = false;
        spell.tooltip.show_header = false;

        return spell;
    }
    private static Spell.Impact.TargetModifier createImpactModifier(String entityType) {
        var condition = new Spell.TargetCondition();
        condition.entity_type = entityType;
        var modifier = new Spell.Impact.TargetModifier();
        modifier.conditions = List.of(condition);
        return modifier;
    }
    private static void bleedImmuneDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#minecraft:undead");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    private static void bossImmuneDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#c:bosses");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }
    private static void poisonImmuneDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#minecraft:ignores_poison_and_regen");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }

    /// MODIFIERS
    public static Entry improved_disabling_shot = add(improved_disabling_shot());
    private static Entry improved_disabling_shot() {
        var id = Identifier.of(MOD_ID, "improved_disabling_shot");
        var title = "Improved Disabling Shot";
        var description = "Increases duration of Disabling Shot by {effect_duration_add} sec";
        var spell = modifierSpellBase();
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:disabling_shot";
        modifier.effect_duration_add = 2;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null);
    }
    public static Entry improved_arctic_volley = add(improved_arctic_volley());
    private static Entry improved_arctic_volley() {
        var id = Identifier.of(MOD_ID, "improved_arctic_volley");
        var title = "Improved Arctic Volley";
        var description = "Reduces cooldown of Arctic Volley by {cooldown_duration_deduct} sec";
        var spell = modifierSpellBase();
        spell.school = ArcherySchools.FROST_RANGED;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:arctic_volley";
        modifier.cooldown_duration_deduct = 3;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null);
    }
    public static Entry improved_point_blank_shot = add(improved_point_blank_shot());
    private static Entry improved_point_blank_shot() {
        var id = Identifier.of(MOD_ID, "improved_point_blank_shot");
        var title = "Improved Point Blank Shot";
        var description = "Increases power multiplier of Point Blank Shot by {power_multiplier_bonus}";
        var spell = modifierSpellBase();
        spell.school = ArcherySchools.FIRE_RANGED;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:point_blank_shot";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.1F;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null);
    }
    /// ACTIVE SPELLS
    public static final Entry disabling_shot = add(disabling_shot());
    private static Entry disabling_shot() {
        var id = Identifier.of(MOD_ID, "disabling_shot");
        var spell = SpellBuilder.createSpellActive();
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
        spell.range = 26;
        spell.tier = 3;

        spell.active.cast.duration = 0.5F;
        spell.active.cast.animation = "spell_engine:archery_pull";
        spell.active.cast.animates_ranged_weapon = true;
        spell.active.cast.sound = new Sound("archers:bow_pull");

        spell.release.animation = "spell_engine:archery_release";
        spell.release.sound = new Sound("entity.arrow.shoot");

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        var shoot = new Spell.Delivery.ShootProjectile();
        shoot.launch_properties.velocity = 1.2F;
        var projectile = new Spell.ProjectileData();
        projectile.client_data = new Spell.ProjectileData.Client();
        projectile.client_data.travel_particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 2,0.01F,0.05F, 0).color(Color.RAGE.toRGBA())
        };
        projectile.client_data.model = new Spell.ProjectileModel();
        projectile.client_data.model.model_id = "archers_expansion:projectile/regular_arrow";
        projectile.client_data.model.scale = 1.0F;
        shoot.projectile = projectile;
        spell.deliver.projectile = shoot;

        var damage = SpellBuilder.Impacts.damage(0.8F, 1.0F);

        var debuff = SpellBuilder.Impacts.effectAdd("archers_expansion:disabling_shot", 2,1,1);
        bossImmuneDeny(debuff);
        debuff.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 10,0.1F,0.3F, 0).color(Color.RAGE.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 10,0.05F,0.3F, 0)
        };
        var custom = new Spell.Impact();
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.apply_to_caster = true;
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "more_rpg_classes:backward_dash_fixed";
        custom.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        ParticleBatch.Rotation.LOOK, 5,0.3F,0.5F, 0).color(Color.WHITE.toRGBA()),
                new ParticleBatch(
                        "poof",
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        ParticleBatch.Rotation.LOOK, 5,0.3F,0.6F, 0)
        };

        spell.impacts = List.of(damage, debuff,custom);

        SpellBuilder.Cost.cooldown(spell, 12);
        SpellBuilder.Cost.exhaust(spell, 0.3F);
        SpellBuilder.Cost.item(spell,"minecraft:arrow",1);

        return new Entry(id, spell, "", "", null);
    }
    public static final Entry point_blank_shot = add(point_blank_shot());
    private static Entry point_blank_shot() {
        var id = Identifier.of(MOD_ID, "point_blank_shot");
        var spell = SpellBuilder.createSpellActive();
        spell.school = ArcherySchools.FIRE_RANGED;
        spell.range = 26;
        spell.tier = 3;

        spell.active.cast.duration = 1.2F;
        spell.active.cast.animation = "spell_engine:archery_pull";
        spell.active.cast.animates_ranged_weapon = true;
        spell.active.cast.sound = new Sound("archers:bow_pull");

        spell.release.animation = "spell_engine:archery_release";
        spell.release.sound = new Sound("item.crossbow.shoot");

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        var shoot = new Spell.Delivery.ShootProjectile();
        shoot.launch_properties.velocity = 2.5F;
        var projectile = new Spell.ProjectileData();
        projectile.client_data = new Spell.ProjectileData.Client();
        projectile.client_data.travel_particles = new ParticleBatch[]{
                new ParticleBatch(
                        "campfire_cosy_smoke",
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 20,0.20F,0.22F, 0).roll(10).rollOffset(180)
        };
        projectile.client_data.model = new Spell.ProjectileModel();
        projectile.client_data.model.model_id = "archers_expansion:projectile/regular_arrow";
        projectile.client_data.model.scale = 1.5F;
        shoot.projectile = projectile;
        spell.deliver.projectile = shoot;

        var damage = SpellBuilder.Impacts.damage(1.1F, 0F);

        var custom = new Spell.Impact();
        bossImmuneDeny(custom);
        custom.action = new Spell.Impact.Action();
        custom.action.custom = new Spell.Impact.Action.Custom();
        custom.action.type = Spell.Impact.Action.Type.CUSTOM;
        custom.action.custom.intent = SpellTarget.Intent.HARMFUL;
        custom.action.custom.handler = "more_rpg_classes:range_scaled_knockback";
        custom.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "poof",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 50,1.0F,2.0F, 0)
        };

        spell.impacts = List.of(damage,custom);

        SpellBuilder.Cost.cooldown(spell, 16);
        SpellBuilder.Cost.exhaust(spell, 0.3F);
        SpellBuilder.Cost.item(spell,"minecraft:arrow",1);

        return new Entry(id, spell, "", "", null);
    }
    public static final Entry choking_gas = add(choking_gas());
    private static Entry choking_gas() {
        var id = Identifier.of(MOD_ID, "choking_gas");
        var spell = SpellBuilder.createSpellActive();
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
        spell.range = 20;
        spell.tier = 4;

        spell.active.cast.duration = 1.25F;
        spell.active.cast.animation = "spell_engine:archery_pull";
        spell.active.cast.animates_ranged_weapon = true;
        spell.active.cast.sound = new Sound("archers:bow_pull");
        spell.active.cast.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.LAUNCH_POINT,
                        ParticleBatch.Rotation.LOOK, 1,0.1F,0.2F, 0).color(Color.POISON_LIGHT.toRGBA())
        };

        spell.release.animation = "spell_engine:archery_release";
        spell.release.sound = new Sound("entity.arrow.shoot");

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        var shoot = new Spell.Delivery.ShootProjectile();
        shoot.launch_properties.velocity = 1.2F;
        var projectile = new Spell.ProjectileData();
        projectile.client_data = new Spell.ProjectileData.Client();
        projectile.client_data.travel_particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 25,0.2F,0.32F, 0).roll(1).rollOffset(180).color(Color.POISON_LIGHT.toRGBA())
        };
        projectile.client_data.model = new Spell.ProjectileModel();
        projectile.client_data.model.model_id = "archers_expansion:projectile/choking_gas_arrow";
        projectile.client_data.model.scale = 1.2F;
        shoot.projectile = projectile;
        spell.deliver.projectile = shoot;

        var damage = SpellBuilder.Impacts.damage(0.75F, 0.5F);
        damage.sound = new Sound("archers_expansion:poison_cloud");
        damage.particles =  new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 35,0.4F,1.2F, 0).color(Color.POISON_LIGHT.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 50,0.1F,0.3F, 0).color(Color.POISON_LIGHT.toRGBA()).preSpawnTravel(10),
                new ParticleBatch(
                        SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 25,0.1F,0.5F, 0).color(Color.POISON_LIGHT.toRGBA()).preSpawnTravel(6),
                new ParticleBatch(
                        SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 25,0.1F,0.3F, 0).color(Color.POISON_LIGHT.toRGBA()).preSpawnTravel(4),
                new ParticleBatch(
                        SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 25,0.05F,0.1F, 0).color(Color.POISON_LIGHT.toRGBA()).preSpawnTravel(2)
        };
        var debuff = SpellBuilder.Impacts.effectSet("archers_expansion:choking_gas",5,0);
        debuff.action.status_effect.amplifier_power_multiplier = 0.15F;
        debuff.action.status_effect.show_particles = false;
        poisonImmuneDeny(debuff);

        spell.impacts = List.of(damage, debuff);

        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.radius = 5.0F;
        spell.area_impact.sound = new Sound("archers_expansion:poison_cloud");
        spell.area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        spell.area_impact.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        2, 0.2F, 0.5F)
        };

        SpellBuilder.Cost.cooldown(spell, 28);
        SpellBuilder.Cost.exhaust(spell, 0.3F);
        SpellBuilder.Cost.item(spell,"minecraft:arrow",1);

        return new Entry(id, spell, "", "", null);
    }
}

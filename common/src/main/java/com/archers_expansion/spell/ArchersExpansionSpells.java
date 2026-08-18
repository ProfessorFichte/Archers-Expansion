package com.archers_expansion.spell;

import com.archers_expansion.effect.ArchersExpansionEffects;
import com.archers_expansion.entity.AlterEgoEntity;
import com.archers_expansion.entity.ExplosiveBarrelEntity;
import com.archers_expansion.entity.ArcherExpansionSummons;
import com.archers_expansion.sounds.Sounds;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.more_rpg_classes.custom.MoreSpellSchools;
import net.spell_engine.api.datagen.SpellBuilder;
import net.spell_engine.api.effect.SpellEngineEffects;
import net.spell_engine.api.render.LightEmission;
import net.spell_engine.api.spell.ExternalSpellSchools;
import net.spell_engine.api.spell.Spell;
import net.spell_engine.api.spell.fx.*;
import net.spell_engine.api.spell.registry.SpellRegistry;
import net.spell_engine.api.util.TriState;
import net.spell_engine.client.gui.SpellTooltip;
import net.spell_engine.client.util.Color;
import net.spell_engine.fx.SpellEngineParticles;
import net.spell_engine.internals.SpellHelper;
import net.spell_engine.internals.target.SpellTarget;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ArchersExpansionSpells {
    public enum Book { DEADEYE, TUNDRA_HUNTER, WAR_ARCHER }
    public record Entry(Identifier id, Spell spell, String title, String description,
                        @Nullable SpellTooltip.DescriptionMutator mutator,
                        @Nullable Book book) {
        public Entry(Identifier id, Spell spell, String title, String description) {
            this(id, spell, title, description, null, null);
        }
        public Entry mutator(SpellTooltip.DescriptionMutator mutator) {
            return new Entry(id, spell, title, description, mutator, book);
        }
        public Entry book(Book book) {
            return new Entry(id, spell, title, description, mutator, book);
        }
    }

    public static final String POISONER =  "poisoner";
    public static final String TRICKSTER =  "trickster";
    public static final String GUARD =  "guard";
    public static final String EXPLOSIVES =  "explosives";
    public static final String ARCTIC =  "arctic";
    public static final String STALKER =  "stalker";

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
    private static void freezeImmuneDeny(Spell.Impact impact) {
        var modifier = createImpactModifier("#minecraft:freeze_immune_entity_types");
        modifier.execute = TriState.DENY;
        impact.target_modifiers = List.of(modifier);
    }

    // Pulls the estimated damage of a helper spell (a hidden sub-spell used for a secondary impact, e.g. an explosion triggered by another spell) into the parent spell's own tooltip.
    private static SpellTooltip.DescriptionMutator helperDamageMutator(Identifier helperId, String token) {
        return (args) -> {
            var world = args.player().getWorld();
            if (world == null) return args.description();
            var optional = SpellRegistry.from(world).getEntry(helperId);
            if (optional.isEmpty()) return args.description();
            var estimated = SpellHelper.estimate(optional.get().value(), args.player(), ItemStack.EMPTY);
            if (estimated.damage().isEmpty()) return args.description();
            var dmg = estimated.damage().get(0);
            return args.description().replace(token, SpellTooltip.formattedRange(dmg.min(), dmg.max()));
        };
    }

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

        return new Entry(id, spell, title, description, null,null);
    }
    public static Entry improved_arctic_volley = add(improved_arctic_volley());
    private static Entry improved_arctic_volley() {
        var id = Identifier.of(MOD_ID, "improved_arctic_volley");
        var title = "Improved Arctic Volley";
        var description = "Reduces cooldown of Arctic Volley by {cooldown_duration_deduct} sec";
        var spell = modifierSpellBase();
        spell.school = MoreSpellSchools.FROST_RANGED;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:arctic_volley";
        modifier.cooldown_duration_deduct = 3;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null,null);
    }
    public static Entry improved_point_blank_shot = add(improved_point_blank_shot());
    private static Entry improved_point_blank_shot() {
        var id = Identifier.of(MOD_ID, "improved_point_blank_shot");
        var title = "Improved Point Blank Shot";
        var description = "Increases power multiplier of Point Blank Shot by {power_multiplier}";
        var spell = modifierSpellBase();
        spell.school = MoreSpellSchools.FIRE_RANGED;

        var modifier = new Spell.Modifier();
        modifier.spell_pattern = "archers_expansion:point_blank_shot";
        modifier.power_modifier = new Spell.Impact.Modifier();
        modifier.power_modifier.power_multiplier = 0.1F;
        spell.modifiers = List.of(modifier);

        return new Entry(id, spell, title, description, null,null);
    }
    public static final Entry fast_shot = add(fast_shot());
    private static Entry fast_shot() {
        var id = Identifier.of(MOD_ID, "fast_shot");
        var spell = SpellBuilder.createSpellActive();
        var title = "Fast Shot";
        var description = "With your next shots, you gain Range Haste.";
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
        spell.range = 0;
        spell.tier = 2;
        spell.group = POISONER;

        spell.release.sound = Sound.withVolume(Identifier.of("archers","marker_shot"),0.5F);
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.STRIPE,
                                SpellEngineParticles.MagicParticles.Motion.FLOAT).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5, 0.1F, 0.2F).color(3208659199L)
        };

        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        spell.deliver.stash_effect.id = "archers_expansion:fast_shot";
        spell.deliver.stash_effect.amplifier = 0;
        spell.deliver.stash_effect.duration = 8.0F;
        var shootTrigger = new Spell.Trigger();
        shootTrigger.type = Spell.Trigger.Type.ARROW_SHOT;
        spell.deliver.stash_effect.triggers = List.of(
                shootTrigger
        );
        spell.deliver.stash_effect.consume = 0;
        spell.deliver.stash_effect.impact_mode = Spell.Delivery.StashEffect.ImpactMode.TRANSFER;

        var buff = SpellBuilder.Impacts.effectAdd("archers_expansion:fast_shot", 4, 1, 4);
        buff.action.status_effect.refresh_duration = true;
        buff.action.status_effect.show_particles = false;
        buff.action.apply_to_caster = true;
        buff.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "electric_spark",
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        15, 0.3F, 1.5F)
        };

        spell.impacts = List.of(buff);

        spell.arrow_perks = new Spell.ArrowPerks();
        spell.arrow_perks.composite_model = SpellBuilder.ProjectileModels.single("archers_expansion:spell_projectile/fast_arrow", 1.0F);
        spell.arrow_perks.bypass_iframes = true;
        spell.arrow_perks.travel_particles = new ParticleBatch[]{
                new ParticleBatch(
                        "electric_spark",
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 20, 0.2F, 0.22F, 0).roll(5)
        };

        SpellBuilder.Cost.cooldown(spell, 15);

        return new Entry(id, spell, title, description, null, Book.DEADEYE);
    }

    public static final Entry bouncing_arrow = add(bouncing_arrow());
    private static Entry bouncing_arrow() {
        var id = Identifier.of(MOD_ID, "bouncing_arrow");
        var spell = SpellBuilder.createSpellActive();
        var title = "Bouncing Arrow";
        var description = "Creates a bouncing arrow, that bounces of targets, deals {damage} damage.";
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
        spell.range = 26;
        spell.tier = 2;
        spell.group = TRICKSTER;

        spell.active.cast.duration = 0.6F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:archery_pull");
        spell.active.cast.animates_ranged_weapon = true;
        spell.active.cast.sound = new Sound("archers:bow_pull");

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.release.animation = PlayerAnimation.of("spell_engine:archery_release");
        spell.release.sound = new Sound("archers_expansion:trick_shot");

        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        var shoot = new Spell.Delivery.ShootProjectile();
        shoot.launch_properties.velocity = 1.2F;
        var projectile = new Spell.ProjectileData();
        projectile.perks = new Spell.ProjectileData.Perks();
        projectile.perks.ricochet = 7;
        projectile.perks.ricochet_range = 26.0F;
        projectile.client_data = new Spell.ProjectileData.Client();
        projectile.client_data.travel_particles = new ParticleBatch[]{
                new ParticleBatch(
                        "crit",
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 2, 0.2F, 0.22F, 0).roll(1),
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.1F, 0.3F)
        };
        projectile.client_data.composite_model = SpellBuilder.ProjectileModels.single("archers_expansion:spell_projectile/regular_arrow", 1.0F);
        shoot.projectile = projectile;
        spell.deliver.projectile = shoot;

        var damage = SpellBuilder.Impacts.damage(0.6F, 0.5F);

        var bleed = SpellBuilder.Impacts.effectSet(SpellEngineEffects.BLEED.id.toString(), 7, 0);
        bleed.action.status_effect.amplifier_power_multiplier = 0.15F;
        bleed.action.status_effect.amplifier_cap = 2;
        bleed.action.status_effect.show_particles = false;
        bleedImmuneDeny(bleed);
        bleed.particles = new ParticleBatch[]{
                new ParticleBatch(
                        "crit",
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.CENTER,
                        5, 0F, 0.1F),
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.1F, 0.3F)
                        .color(Color.RAGE.toRGBA())
        };
        bleed.sound = new Sound("entity.arrow.hit");

        spell.impacts = List.of(damage, bleed);

        SpellBuilder.Cost.cooldown(spell, 10);
        SpellBuilder.Cost.exhaust(spell, 0.3F);
        SpellBuilder.Cost.item(spell, "minecraft:arrow", 1);

        return new Entry(id, spell, title, description, null,Book.DEADEYE);
    }
    public static final Entry disabling_shot = add(disabling_shot());
    private static Entry disabling_shot() {
        var id = Identifier.of(MOD_ID, "disabling_shot");
        var spell = SpellBuilder.createSpellActive();
        var title = "Disabling Shot";
        var description = "Jumps back after shooting a target, slows for {effect_duration} seconds and damages the target for {damage} damage.";
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
        spell.range = 26;
        spell.tier = 3;
        spell.group = TRICKSTER;

        spell.active.cast.duration = 0.5F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:archery_pull");
        spell.active.cast.animates_ranged_weapon = true;
        spell.active.cast.sound = new Sound("archers:bow_pull");

        spell.release.animation = PlayerAnimation.of("spell_engine:archery_release");
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
        projectile.client_data.composite_model = SpellBuilder.ProjectileModels.single("archers_expansion:spell_projectile/regular_arrow", 1.0F);
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
        var leap = SpellBuilder.Impacts.leap(-3.0F, 0.7F);
        leap.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        ParticleBatch.Rotation.LOOK, 5,0.3F,0.5F, 0).color(Color.WHITE.toRGBA()),
                new ParticleBatch(
                        "poof",
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        ParticleBatch.Rotation.LOOK, 5,0.3F,0.6F, 0)
        };

        spell.impacts = List.of(damage, debuff,leap);

        SpellBuilder.Cost.cooldown(spell, 12);
        SpellBuilder.Cost.exhaust(spell, 0.3F);
        SpellBuilder.Cost.item(spell,"minecraft:arrow",1);

        return new Entry(id, spell, title, description, null,Book.DEADEYE);
    }
    public static final Entry choking_gas = add(choking_gas());
    private static Entry choking_gas() {
        var id = Identifier.of(MOD_ID, "choking_gas");
        var spell = SpellBuilder.createSpellActive();
        var title = "Choking Gas";
        var description = "Creates a poisonous gas cloud on projectile contact and deals {damage} damage.";
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
        spell.range = 20;
        spell.tier = 4;
        spell.group = POISONER;

        spell.active.cast.duration = 1.25F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:archery_pull");
        spell.active.cast.animates_ranged_weapon = true;
        spell.active.cast.sound = new Sound("archers:bow_pull");
        spell.active.cast.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.LAUNCH_POINT,
                        ParticleBatch.Rotation.LOOK, 1,0.1F,0.2F, 0).color(Color.POISON_LIGHT.toRGBA())
        };

        spell.release.animation = PlayerAnimation.of("spell_engine:archery_release");
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
        projectile.client_data.composite_model = SpellBuilder.ProjectileModels.single("archers_expansion:spell_projectile/choking_gas_arrow", 1.2F);
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

        return new Entry(id, spell, title, description, null, Book.DEADEYE);
    }

    public static final Entry venom_cask = add(venom_cask());
    private static Entry venom_cask() {
        var id = Identifier.of(MOD_ID, "venom_cask");
        var spell = SpellBuilder.createSpellActive();
        var title = "Venom Cask";
        var description = "Throws a cask of venom that shatters on impact, dealing {damage} damage. Leaves a poisonous cloud for {poison_duration}s that poisons those inside.";
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
        spell.range = 3;
        spell.tier = 3;
        spell.group = POISONER;
        spell.secondary_archetype = Spell.ExtendedArchetype.ANY;

        var charge = SpellBuilder.Casting.charge(spell, 1.2F);
        charge.min_release_ratio = 0.25F;
        charge.bonus.range_add = 11;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:one_handed_throw_charge");

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.release.animation = PlayerAnimation.of("spell_engine:one_handed_throw_release");
        spell.release.sound = new Sound(Sounds.VENOM_CASK_THROW.id());
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.LAUNCH_POINT,
                        5, 0.1F, 0.2F).color(Color.POISON_LIGHT.toRGBA())
        };

        spell.deliver.type = Spell.Delivery.Type.CUSTOM;
        spell.deliver.custom = new Spell.Delivery.Custom();
        spell.deliver.custom.handler = "archers_expansion:venom_flask";

        var damage = SpellBuilder.Impacts.damage(0.6F, 0F);
        damage.sound = new Sound("block.glass.break");
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        20, 0.2F, 0.5F).color(Color.POISON_LIGHT.toRGBA())
        };

        var poison = SpellBuilder.Impacts.effectSet("minecraft:poison", 6, 1);
        poison.action.status_effect.amplifier_power_multiplier = 0.1F;
        poison.action.status_effect.show_particles = false;
        poisonImmuneDeny(poison);
        poison.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        15, 0.2F, 0.4F).color(Color.POISON_LIGHT.toRGBA())
        };

        var cloud = new Spell.Impact();
        cloud.action = new Spell.Impact.Action();
        cloud.action.custom = new Spell.Impact.Action.Custom();
        cloud.action.type = Spell.Impact.Action.Type.CUSTOM;
        cloud.action.custom.intent = SpellTarget.Intent.HARMFUL;
        cloud.action.custom.handler = "archers_expansion:venom_cask_cloud_impact";

        spell.impacts = List.of(damage, poison, cloud);

        SpellBuilder.Cost.cooldown(spell, 16);
        SpellBuilder.Cost.exhaust(spell, 0.3F);

        SpellTooltip.DescriptionMutator mutator = (args) -> {
            var world = args.player().getWorld();
            if (world == null) return args.description();
            var optional = SpellRegistry.from(world).getEntry(Identifier.of(MOD_ID, "venom_cask_cloud"));
            if (optional.isEmpty()) return args.description();
            var cloudSpell = optional.get().value();
            if (cloudSpell.deliver.clouds == null || cloudSpell.deliver.clouds.isEmpty()) return args.description();
            var seconds = (int) cloudSpell.deliver.clouds.get(0).time_to_live_seconds;
            return args.description().replace("{poison_duration}", String.valueOf(seconds));
        };
        return new Entry(id, spell, title, description, mutator, Book.DEADEYE);
    }
    public static final Entry VENOM_CASK_CLOUD = add(VENOM_CASK_CLOUD());
    private static Entry VENOM_CASK_CLOUD() {
        var id = Identifier.of(MOD_ID, "venom_cask_cloud");
        var title = "";
        var description = "";

        var spell = SpellBuilder.createSpellActive();
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
        spell.range = 0;
        spell.tier = 3;

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        var cloud = new Spell.Delivery.Cloud();
        cloud.spawn_ticks = 8;
        cloud.despawn_ticks = 15;
        cloud.volume.radius = 3.0F;
        cloud.volume.area = new Spell.Target.Area();
        cloud.volume.sound = Sound.withVolume(Identifier.of("archers_expansion:poison_cloud"), 0.3F);
        cloud.impact_tick_interval = 25;
        cloud.time_to_live_seconds = 6;
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        int venomCaskTotalTicks = (int) (cloud.time_to_live_seconds * 20);
        float venomBlobScale = 2.0F;
        cloud.client_data.model_fx = List.of(
                ModelEffectBuilder.create("archers_expansion:spell_effect/venom_blob")
                        .light(LightEmission.GLOW_TRANSLUCENT)
                        .positioning(0F)
                        .scale(venomBlobScale)
                        .initialTranslateY(0.5F * (venomBlobScale - 1F) + 0.2F)
                        .duration(venomCaskTotalTicks)
                        .scaleIn(0, cloud.spawn_ticks, ModelEffect.Easing.EASE_OUT_BOUNCE)
                        .scaleOut(venomCaskTotalTicks - cloud.despawn_ticks, venomCaskTotalTicks, ModelEffect.Easing.EASE_IN_CUBIC)
                        .build()
        );
        spell.deliver.clouds = List.of(cloud);

        var poison = SpellBuilder.Impacts.effectAdd_ScaledCap("minecraft:poison", 6, 0.1F);
        poison.action.status_effect.show_particles = false;
        poisonImmuneDeny(poison);
        poison.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        10, 0.2F, 0.4F).color(Color.POISON_LIGHT.toRGBA())
        };

        var damage = SpellBuilder.Impacts.damage(0.2F);

        spell.impacts = List.of(poison, damage);

        SpellBuilder.Cost.cooldown(spell, 1);

        return new Entry(id, spell, title, description, null, null);
    }
    public static final Entry ALTER_EGO = add(ALTER_EGO());
    private static Entry ALTER_EGO() {
        var id = Identifier.of(MOD_ID, "alter_ego");
        var title = "Alter Ego";
        var description = "Creates decoys and grants invisibility for {effect_duration} sec. Decoys explode for {explosion_damage} damage when destroyed, their duration expires or a enemy collides with them.";

        var spell = SpellBuilder.createSpellActive();
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
        spell.range = 4;
        spell.tier = 4;
        spell.secondary_archetype = Spell.ExtendedArchetype.ANY;
        spell.group = TRICKSTER;

        spell.active.cast.duration = 0F;

        spell.release.animation = PlayerAnimation.of("spell_engine:one_handed_area_release");
        spell.release.sound = new Sound(Sounds.ALTER_EGO_VANISH.id());
        spell.release.particles = new ParticleBatch[] {
                new ParticleBatch(
                        SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        50, 1F, 1F).color(Color.WHITE.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        50, 1F, 1F).color(Color.WHITE.toRGBA())
        };
        spell.release.particles_scaled_with_ranged = new ParticleBatch[] {
                new ParticleBatch(
                        SpellEngineParticles.area_effect_293.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.GROUND,
                        1, 0, 0)
                        .scale(0.8F)
                        .color(Color.BLUE.toRGBA())
        };

        var vanish = SpellBuilder.Impacts.effectSet(ArchersExpansionEffects.INFILTRATORS_VANISH.id.toString(),8,0);
        vanish.action.status_effect.show_particles = false;

        int egoDelay = 10;
        int egoLiveSeconds = 12;
        var spawn = new Spell.Impact();
        spawn.action = new Spell.Impact.Action();
        spawn.action.type = Spell.Impact.Action.Type.SPAWN;
        var ego1 = new Spell.Impact.Action.Spawn();
        ego1.entity_type_id = AlterEgoEntity.ENTITY_TYPE.getRegistryEntry().getKey().get().getValue().toString();
        ego1.delay_ticks = egoDelay;
        ego1.time_to_live_seconds = egoLiveSeconds;
        ego1.placement.location_offset_by_look = 3.0F;
        ego1.placement.apply_yaw = true;
        var ego2 = new Spell.Impact.Action.Spawn();
        ego2.entity_type_id = AlterEgoEntity.ENTITY_TYPE.getRegistryEntry().getKey().get().getValue().toString();
        ego2.delay_ticks = egoDelay;
        ego2.time_to_live_seconds = egoLiveSeconds;
        ego2.placement.location_offset_by_look = 3.0F;
        ego2.placement.location_yaw_offset = 120.0F;
        ego2.placement.apply_yaw = true;
        var ego3 = new Spell.Impact.Action.Spawn();
        ego3.entity_type_id = AlterEgoEntity.ENTITY_TYPE.getRegistryEntry().getKey().get().getValue().toString();
        ego3.delay_ticks = egoDelay;
        ego3.time_to_live_seconds = egoLiveSeconds;
        ego3.placement.location_offset_by_look = 3.0F;
        ego3.placement.location_yaw_offset = -120.0F;
        ego3.placement.apply_yaw = true;

        spawn.action.spawns = List.of(ego1,ego2, ego3);

        spell.impacts = List.of(vanish,spawn);

        SpellBuilder.Cost.cooldown(spell, 40);
        spell.cost.exhaust = 0.4F;

        var mutator = helperDamageMutator(Identifier.of(MOD_ID, "alter_ego_explosion"), "{explosion_damage}");
        return new Entry(id, spell, title, description, mutator, Book.DEADEYE);
    }
    public static final Entry ALTER_EGO_EXPLOSION = add(ALTER_EGO_EXPLOSION());
    private static Entry ALTER_EGO_EXPLOSION() {
        var id = Identifier.of(MOD_ID, "alter_ego_explosion");
        var title = "";
        var description = "";

        var spell = SpellBuilder.createSpellActive();
        spell.school = ExternalSpellSchools.PHYSICAL_RANGED;
        spell.range = 4;
        spell.tier = 4;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.vertical_range_multiplier = 0.5F;

        spell.release = new Spell.Release();
        spell.release.sound = new Sound(Sounds.ALTER_EGO_EXPLOSION.id());
        spell.release.particles = new ParticleBatch[] {
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.GROUND,
                        10, 0.2F, 0.6F),
                new ParticleBatch(
                        SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.GROUND,
                        10, 0.5F, 0.9F),
                new ParticleBatch(
                        SpellEngineParticles.area_effect_574.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        1, 0, 0)
                        .scale(2.0F)
                        .color(Color.BLUE.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.aura_effect_574.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        1, 0, 0)
                        .scale(2.0F)
                        .color(Color.BLUE.toRGBA()),
        };

        var damage = SpellBuilder.Impacts.damage(0.5F, 0.8F);
        damage.particles = new ParticleBatch[] {
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5, 0.2F, 0.7F)
        };

        spell.impacts = List.of(damage);

        SpellBuilder.Cost.cooldown(spell, 1);

        return new Entry(id, spell, title, description, null,null);
    }
    public static final Entry frozen_shot = add(frozen_shot());
    private static Entry frozen_shot() {
        var id = Identifier.of(MOD_ID, "frozen_shot");
        var spell = SpellBuilder.createSpellActive();
        var title = "Frozen Shot";
        var description = "Your next shots stack the frosted effect on targets, slowing them and freezing";
        spell.school = MoreSpellSchools.FROST_RANGED;
        spell.range = 0;
        spell.tier = 2;
        spell.group = STALKER;

        spell.release.sound = new Sound("spell_engine:generic_frost_impact");

        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        spell.deliver.stash_effect.id = "archers_expansion:frozen_shot";
        spell.deliver.stash_effect.amplifier = 6;
        spell.deliver.stash_effect.duration = 10.0F;
        var shootTrigger = new Spell.Trigger();
        shootTrigger.type = Spell.Trigger.Type.ARROW_SHOT;
        spell.deliver.stash_effect.triggers = List.of(
                shootTrigger
        );
        spell.deliver.stash_effect.impact_mode = Spell.Delivery.StashEffect.ImpactMode.TRANSFER;

        var frost = SpellBuilder.Impacts.effectAdd("more_rpg_classes:frosted", 10, 1, 4);
        frost.action.status_effect.refresh_duration = true;
        freezeImmuneDeny(frost);
        frost.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.frost_shard.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        5, 0.1F, 0.35F)
        };

        spell.impacts = List.of(frost);

        spell.arrow_perks = new Spell.ArrowPerks();
        spell.arrow_perks.composite_model = SpellBuilder.ProjectileModels.single("archers_expansion:spell_projectile/glacial_arrow", 1.0F, LightEmission.RADIATE);
        spell.arrow_perks.bypass_iframes = true;
        spell.arrow_perks.travel_particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 20, 0.2F, 0.22F, 0).roll(5),
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 5, 0F, 0.05F, 0)
        };

        SpellBuilder.Cost.cooldown(spell, 10);

        return new Entry(id, spell, title, description, null,Book.TUNDRA_HUNTER);
    }

    public static final Entry frozen_pact = add(frozen_pact());
    private static Entry frozen_pact() {
        var id = Identifier.of(MOD_ID, "frozen_pact");
        var spell = SpellBuilder.createSpellActive();
        var title = "Frozen Pact";
        var description = "Damages the target according to their Frozen ticks and reduces its attack for {effect_duration} seconds.";
        spell.school = MoreSpellSchools.FROST_RANGED;
        spell.range = 12;
        spell.tier = 2;
        spell.group = ARCTIC;
        spell.secondary_archetype = Spell.ExtendedArchetype.ANY;

        spell.active.cast.duration = 0.5F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:one_handed_area_charge");
        spell.active.cast.sound = new Sound("spell_engine:generic_frost_casting");
        spell.active.cast.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.PIPE, ParticleBatch.Origin.CENTER,
                        0.5F, 0.1F, 0.2F)
        };

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;
        spell.target.area.angle_degrees = 360;
        spell.target.area.vertical_range_multiplier = 0.5F;

        spell.release.animation = PlayerAnimation.of("spell_engine:one_handed_area_release");
        spell.release.sound = new Sound("spell_engine:generic_frost_release");

        var debuff = SpellBuilder.Impacts.effectSet("archers_expansion:frozen_pact", 5, 0);
        debuff.action.status_effect.show_particles = false;
        freezeImmuneDeny(debuff);
        debuff.particles = new ParticleBatch[]{
                new ParticleBatch(
                                                SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST
                        ).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        50, 0.2F, 0.7F),
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        25, 0.1F, 0.4F)
        };
        debuff.sound = new Sound("spell_engine:generic_frost_impact");

        spell.impacts = List.of(debuff);

        SpellBuilder.Cost.cooldown(spell, 22);
        SpellBuilder.Cost.exhaust(spell, 0.3F);

        return new Entry(id, spell, title, description, null,Book.TUNDRA_HUNTER);
    }
    public static final Entry arctic_volley = add(arctic_volley());
    private static Entry arctic_volley() {
        var id = Identifier.of(MOD_ID, "arctic_volley");
        var spell = SpellBuilder.createSpellActive();
        var title = "Arctic Volley";
        var description = "Shots 8 arctic arrows that deal {damage} damage and frosts targets.";
        spell.school = MoreSpellSchools.FROST_RANGED;
        spell.range = 32;
        spell.tier = 3;
        spell.group = ARCTIC;

        spell.active.cast.duration = 0.5F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:archery_pull");
        spell.active.cast.animates_ranged_weapon = true;
        spell.active.cast.sound = new Sound("archers:bow_pull");

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.release.animation = PlayerAnimation.of("spell_engine:archery_release");
        spell.release.sound = new Sound("spell_engine:generic_frost_impact");

        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        var shoot = new Spell.Delivery.ShootProjectile();
        shoot.launch_properties.velocity = 1.2F;
        shoot.launch_properties.extra_launch_count = 8;
        shoot.direction_offsets = new Spell.Delivery.ShootProjectile.DirectionOffset[] {
                new Spell.Delivery.ShootProjectile.DirectionOffset(0, 0),
                new Spell.Delivery.ShootProjectile.DirectionOffset(-15, 0),
                new Spell.Delivery.ShootProjectile.DirectionOffset(15, 0),
                new Spell.Delivery.ShootProjectile.DirectionOffset(-30, 0),
                new Spell.Delivery.ShootProjectile.DirectionOffset(30, 0),
                new Spell.Delivery.ShootProjectile.DirectionOffset(0, -15),
                new Spell.Delivery.ShootProjectile.DirectionOffset(0, 15),
                new Spell.Delivery.ShootProjectile.DirectionOffset(-15, -15),
                new Spell.Delivery.ShootProjectile.DirectionOffset(15, 15)
        };
        var projectile = new Spell.ProjectileData();
        projectile.client_data = new Spell.ProjectileData.Client();
        projectile.client_data.travel_particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST).id().toString(),
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 10, 0.2F, 0.22F, 0).roll(5),
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 3, 0F, 0.05F, 0)
        };
        projectile.client_data.composite_model = SpellBuilder.ProjectileModels.single("archers_expansion:spell_projectile/glacial_arrow", 1.0F);
        projectile.client_data.light_level = 10;
        shoot.projectile = projectile;
        spell.deliver.projectile = shoot;

        var damage = SpellBuilder.Impacts.damage(0.15F, 0F);

        var frost = SpellBuilder.Impacts.effectSet("more_rpg_classes:frosted", 5, 0);
        frost.action.status_effect.amplifier_power_multiplier = 0.05F;
        frost.action.status_effect.show_particles = false;
        freezeImmuneDeny(frost);
        frost.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5, 0.1F, 0.3F),
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST
                        ).id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        10, 0.3F, 0.6F)
        };
        frost.sound = new Sound("entity.arrow.hit");

        spell.impacts = List.of(damage, frost);

        SpellBuilder.Cost.cooldown(spell, 20);
        SpellBuilder.Cost.exhaust(spell, 0.3F);
        SpellBuilder.Cost.item(spell, "minecraft:arrow", 1);

        return new Entry(id, spell, title, description, null, Book.TUNDRA_HUNTER);
    }
    public static final Entry enchanted_crystal_arrow = add(enchanted_crystal_arrow());
    private static Entry enchanted_crystal_arrow() {
        var id = Identifier.of(MOD_ID, "enchanted_crystal_arrow");
        var spell = SpellBuilder.createSpellActive();
        var title = "Enchanted Crystal Arrow";
        var description = "Deals {damage} damage, stuns and freezes the target.";
        spell.school = MoreSpellSchools.FROST_RANGED;
        spell.range = 32;
        spell.tier = 4;
        spell.group = ARCTIC;

        var charge = SpellBuilder.Casting.charge(spell, 2.0F);
        charge.min_release_ratio = 0.25F;
        charge.bonus.range_add = 32;
        charge.bonus.projectile_scale_multiply = 0.75F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:archery_pull");
        spell.active.cast.animates_ranged_weapon = true;
        spell.active.cast.sound = new Sound("archers:bow_pull");
        spell.active.cast.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.PIPE, ParticleBatch.Origin.CENTER,
                        0.5F, 0.1F, 0.2F)
        };

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.release.animation = PlayerAnimation.of("spell_engine:archery_release");
        spell.release.sound = new Sound("archers_expansion:special_shot");

        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        var shoot = new Spell.Delivery.ShootProjectile();
        shoot.launch_properties.velocity = 2.0F;
        var projectile = new Spell.ProjectileData();
        projectile.perks = new Spell.ProjectileData.Perks();
        projectile.homing_angle = 25.0F;
        projectile.client_data = new Spell.ProjectileData.Client();
        projectile.client_data.travel_particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST
                        ).id().toString(),
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 20, 0.2F, 0.22F, 0).roll(10).rollOffset(180),
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST
                        ).id().toString(),
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 20, 0.2F, 0.32F, 0).roll(10),
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 10, 0F, 0.05F, 0)
        };
        projectile.client_data.composite_model = SpellBuilder.ProjectileModels.single("archers_expansion:spell_projectile/glacial_arrow", 1.5F);
        projectile.client_data.light_level = 14;
        shoot.projectile = projectile;
        spell.deliver.projectile = shoot;

        var damage = SpellBuilder.Impacts.damage(1.2F, 0.5F);
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST
                        ).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        50, 0.2F, 0.7F),
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        25, 0.1F, 0.4F)
        };
        damage.sound = new Sound("archers_expansion:enchanted_crystal_arrow_impact");

        var stun = SpellBuilder.Impacts.effectSet("archers_expansion:enchanted_crystal_arrow", 3, 0);
        stun.action.status_effect.show_particles = false;
        freezeImmuneDeny(stun);

        spell.impacts = List.of(damage, stun);

        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.radius = 3.0F;
        spell.area_impact.area = new Spell.Target.Area();
        spell.area_impact.area.distance_dropoff = Spell.Target.Area.DropoffCurve.SQUARED;

        SpellBuilder.Cost.cooldown(spell, 42);
        SpellBuilder.Cost.exhaust(spell, 0.3F);
        SpellBuilder.Cost.item(spell, "minecraft:arrow", 1);

        return new Entry(id, spell, title, description, null, Book.TUNDRA_HUNTER);
    }
    public static final Entry bearward = add(bearward());
    private static Entry bearward() {
        var id = Identifier.of(MOD_ID, "bearward");
        var spell = SpellBuilder.createSpellActive();
        var title = "Polar Bearward";
        var description = "Summons a Polar Bear to fight by your side for "
                + SpellTooltip.placeholder(SpellTooltip.summonDurationToken) + " sec, empowered by your Ranged Damage. " +
                "The Bear gets a short raging speed boost if its target is some distance away.";
        spell.school = MoreSpellSchools.FROST_RANGED;
        spell.range = 0;
        spell.tier = 4;
        spell.secondary_archetype = Spell.ExtendedArchetype.ANY;
        spell.group = STALKER;


        spell.release.animation = PlayerAnimation.of("more_rpg_classes:two_handed_roar");
        spell.release.sound = new Sound("spell_engine:generic_frost_release");
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST
                        ).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        40, 0.2F, 0.6F)
        };

        var summon = new Spell.Impact();
        summon.action = new Spell.Impact.Action();
        summon.action.type = Spell.Impact.Action.Type.SUMMON;
        summon.action.summon = ArcherExpansionSummons.summon();
        spell.impacts = List.of(summon);

        SpellBuilder.Cost.cooldown(spell, 60);
        spell.cost.cooldown.haste_affected = false;
        SpellBuilder.Cost.exhaust(spell, 0.4F);

        return new Entry(id, spell, title, description, null, Book.TUNDRA_HUNTER);
    }
    private static List<ModelEffect> fusilladeFx(int spawnTicks, int despawnTicks, int totalTicks) {
        float modelScale = 4.0F;
        var flake = ModelEffectBuilder.create("archers_expansion:spell_effect/frozen_fussilade")
                .light(LightEmission.GLOW_TRANSLUCENT)
                .positioning(0F)
                .scale(modelScale)
                .initialTranslateY(0.5F * (modelScale - 1F)+0.2F)
                .duration(totalTicks)
                .scaleIn(0, spawnTicks, ModelEffect.Easing.EASE_OUT_CUBIC)
                .scaleOut(totalTicks - despawnTicks, totalTicks, ModelEffect.Easing.EASE_IN_CUBIC)
                .build();
        return List.of(flake);
    }
    public static final Entry frozen_fusillade = add(frozen_fusillade());
    private static Entry frozen_fusillade() {
        var id = Identifier.of(MOD_ID, "frozen_fusillade");
        var spell = SpellBuilder.createSpellActive();
        var title = "Frozen Fusillade";
        var description = "Summons a barrage of ice around you, slowing nearby enemies and speeding your allies for {effect_duration_1} seconds.";
        spell.school = MoreSpellSchools.FROST_RANGED;
        spell.range = 0;
        spell.tier = 3;
        spell.secondary_archetype = Spell.ExtendedArchetype.ANY;
        spell.group = STALKER;

        spell.active.cast.duration = 0.5F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:one_handed_area_charge");
        spell.active.cast.sound = new Sound("spell_engine:generic_frost_casting");
        spell.active.cast.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.PIPE, ParticleBatch.Origin.CENTER,
                        0.5F, 0.1F, 0.2F)
        };

        spell.release.animation = PlayerAnimation.of("spell_engine:one_handed_area_release");
        spell.release.sound = new Sound("spell_engine:generic_frost_release");
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST
                        ).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        2, 0.05F, 0.1F)
        };

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        var cloud = new Spell.Delivery.Cloud();
        cloud.spawn_ticks = 10;
        cloud.despawn_ticks = 20;
        cloud.volume.radius = 6.0F;
        cloud.volume.area = new Spell.Target.Area();
        cloud.volume.sound = Sound.withVolume(Identifier.of("spell_engine:generic_frost_charging"),0.2F);
        cloud.impact_tick_interval = 10;
        cloud.time_to_live_seconds = 6;
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 10;
        cloud.client_data.model_fx = fusilladeFx(cloud.spawn_ticks, cloud.despawn_ticks,
                cloud.spawn_ticks + Math.round(cloud.time_to_live_seconds * 20F) + cloud.despawn_ticks);
        cloud.client_data.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.PILLAR, ParticleBatch.Origin.FEET,
                        1, 0.05F, 0.1F)
        };
        cloud.placement = new Spell.EntityPlacement();
        cloud.placement.force_onto_ground = true;
        cloud.placement.location_offset_y = 0;
        spell.deliver.clouds = List.of(cloud);

        var slow = SpellBuilder.Impacts.effectSet(ArchersExpansionEffects.FROZEN_FUSILLADE_SLOW.id.toString(), 3, 0);
        slow.action.status_effect.show_particles = false;
        freezeImmuneDeny(slow);
        slow.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.MagicParticles.get(
                                SpellEngineParticles.MagicParticles.Shape.FROST,
                                SpellEngineParticles.MagicParticles.Motion.BURST
                        ).id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        15, 0.2F, 0.4F)
        };

        var haste = SpellBuilder.Impacts.effectSet(ArchersExpansionEffects.FROZEN_FUSILLADE_HASTE.id.toString(), 3, 0);
        haste.action.status_effect.show_particles = false;
        haste.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.snowflake.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        10, 0.2F, 0.4F)
        };

        spell.impacts = List.of(slow, haste);

        SpellBuilder.Cost.cooldown(spell, 24);
        SpellBuilder.Cost.exhaust(spell, 0.3F);

        return new Entry(id, spell, title, description, null, Book.TUNDRA_HUNTER);
    }
    public static final Entry dual_shot = add(dual_shot());
    private static Entry dual_shot() {
        var id = Identifier.of(MOD_ID, "dual_shot");
        var spell = SpellBuilder.createSpellActive();
        var title = "Dual Shot";
        var description = "Fires two Arrows at once.";
        spell.school = MoreSpellSchools.FIRE_RANGED;
        spell.range = 0;
        spell.tier = 2;
        spell.group = GUARD;

        spell.active.cast.duration = 0.35F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:archery_pull");
        spell.active.cast.animates_ranged_weapon = true;
        spell.active.cast.sound = new Sound("archers:bow_pull");

        spell.release.animation = PlayerAnimation.of("spell_engine:archery_release");

        spell.deliver.type = Spell.Delivery.Type.SHOOT_ARROW;
        spell.deliver.shoot_arrow = new Spell.Delivery.ShootArrow();
        spell.deliver.shoot_arrow.divergence = 10.0F;
        spell.deliver.shoot_arrow.launch_properties = new Spell.LaunchProperties();
        spell.deliver.shoot_arrow.launch_properties.velocity = 3.15F;
        spell.deliver.shoot_arrow.launch_properties.extra_launch_count = 1;

        spell.arrow_perks = new Spell.ArrowPerks();
        spell.arrow_perks.damage_multiplier = 0.7F;
        spell.arrow_perks.bypass_iframes = true;
        spell.arrow_perks.knockback = 0.5F;

        SpellBuilder.Cost.cooldown(spell, 7);
        spell.cost.item = new Spell.Cost.Item();
        spell.cost.item.id = "arrow";
        spell.cost.item.consume = false;

        return new Entry(id, spell, title, description, null,Book.WAR_ARCHER);
    }
    public static final Entry smoldering_arrow = add(smoldering_arrow());
    private static Entry smoldering_arrow() {
        var id = Identifier.of(MOD_ID, "smoldering_arrow");
        var spell = SpellBuilder.createSpellActive();
        var title = "Smoldering Arrow";
        var description = "Your next shot creates a small explosion near the target, damaging and burning entities around.";
        spell.school = MoreSpellSchools.FIRE_RANGED;
        spell.range = 0;
        spell.tier = 2;
        spell.group = EXPLOSIVES;

        spell.release.sound = Sound.withVolume(Identifier.of("entity.generic.extinguish_fire"), 0.5F);
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.flame.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5, 0.1F, 0.2F)
        };

        spell.deliver.type = Spell.Delivery.Type.STASH_EFFECT;
        spell.deliver.stash_effect = new Spell.Delivery.StashEffect();
        spell.deliver.stash_effect.id = "archers_expansion:smoldering_arrows";
        spell.deliver.stash_effect.amplifier = 2;
        spell.deliver.stash_effect.duration = 10.0F;
        var shootTrigger = new Spell.Trigger();
        shootTrigger.type = Spell.Trigger.Type.ARROW_SHOT;
        spell.deliver.stash_effect.triggers = List.of(
                shootTrigger
        );
        spell.deliver.stash_effect.impact_mode = Spell.Delivery.StashEffect.ImpactMode.TRANSFER;

        var damage = SpellBuilder.Impacts.damage(0.3F, 0F);
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.fire_explosion.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        1, 0.2F, 0.5F),
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_b.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        25, 0.1F, 0.3F).preSpawnTravel(2),
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_b.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        25, 0.2F, 0.5F).preSpawnTravel(4)
        };
        damage.sound = new Sound("entity.generic.explode");

        var fire = SpellBuilder.Impacts.fire(2);

        spell.impacts = List.of(damage, fire);

        spell.area_impact = new Spell.AreaImpact();
        spell.area_impact.radius = 2.0F;

        spell.arrow_perks = new Spell.ArrowPerks();
        spell.arrow_perks.composite_model = SpellBuilder.ProjectileModels.single("archers_expansion:spell_projectile/smoldering_arrow", 1.0F, LightEmission.RADIATE);
        spell.arrow_perks.bypass_iframes = true;
        spell.arrow_perks.travel_particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_b.id().toString(),
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 20, 0.2F, 0.22F, 0).roll(5),
                new ParticleBatch(
                        "smoke",
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 5, 0F, 0.05F, 0)
        };

        SpellBuilder.Cost.cooldown(spell, 12);

        return new Entry(id, spell, title, description, null, Book.WAR_ARCHER);
    }
    public static final Entry point_blank_shot = add(point_blank_shot());
    private static Entry point_blank_shot() {
        var id = Identifier.of(MOD_ID, "point_blank_shot");
        var spell = SpellBuilder.createSpellActive();
        var title = "Point-Blank Shot";
        var description = "Deals {damage} damage, also gets knocked back more, the closer it is to the caster.";
        spell.school = MoreSpellSchools.FIRE_RANGED;
        spell.range = 26;
        spell.tier = 3;
        spell.group = GUARD;

        spell.active.cast.duration = 1.2F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:archery_pull");
        spell.active.cast.animates_ranged_weapon = true;
        spell.active.cast.sound = new Sound("archers:bow_pull");

        spell.release.animation = PlayerAnimation.of("spell_engine:archery_release");
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
        projectile.client_data.composite_model = SpellBuilder.ProjectileModels.single("archers_expansion:spell_projectile/regular_arrow", 3.0F);
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

        return new Entry(id, spell, title, description, null,Book.WAR_ARCHER);
    }
    public static final Entry pin_down = add(pin_down());
    private static Entry pin_down() {
        var id = Identifier.of(MOD_ID, "pin_down");
        var spell = SpellBuilder.createSpellActive();
        var title = "Pin Down";
        var description = "Deals {damage} damage, the target cant move or jump for {effect_duration} seconds.";
        spell.school = MoreSpellSchools.FIRE_RANGED;
        spell.range = 40;
        spell.tier = 4;
        spell.group = GUARD;

        spell.active.cast.duration = 1.5F;
        spell.active.cast.animation = PlayerAnimation.of("spell_engine:archery_pull");
        spell.active.cast.sound = new Sound("archers:bow_pull");

        spell.target.type = Spell.Target.Type.AIM;
        spell.target.aim = new Spell.Target.Aim();

        spell.release.animation = PlayerAnimation.of("spell_engine:archery_release");
        spell.release.sound = new Sound("item.crossbow.shoot");

        spell.deliver.type = Spell.Delivery.Type.PROJECTILE;
        var shoot = new Spell.Delivery.ShootProjectile();
        shoot.launch_properties.velocity = 2.0F;
        var projectile = new Spell.ProjectileData();
        projectile.client_data = new Spell.ProjectileData.Client();
        projectile.client_data.travel_particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 20, 0.2F, 0.22F, 0).roll(10).rollOffset(180)
                        .color(Color.RAGE.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.LINE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 20, 0.2F, 0.32F, 0).roll(10)
                        .color(Color.RAGE.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.CENTER,
                        ParticleBatch.Rotation.LOOK, 10, 0F, 0.05F, 0)
                        .color(Color.RAGE.toRGBA()),
        };
        projectile.client_data.composite_model = SpellBuilder.ProjectileModels.single("archers_expansion:spell_projectile/pin_down_arrow", 1.2F);
        shoot.projectile = projectile;
        spell.deliver.projectile = shoot;

        var damage = SpellBuilder.Impacts.damage(1.2F, 0F);
        damage.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.dripping_blood.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        10, 0.1F, 0.3F)
        };
        damage.sound = new Sound("archers_expansion:pin_down");

        var immobilize = SpellBuilder.Impacts.effectSet(SpellEngineEffects.IMMOBILIZE.id.toString(), 4, 0);
        immobilize.action.status_effect.show_particles = false;
        bossImmuneDeny(immobilize);

        spell.impacts = List.of(damage, immobilize);

        SpellBuilder.Cost.cooldown(spell, 28);
        SpellBuilder.Cost.exhaust(spell, 0.3F);
        SpellBuilder.Cost.item(spell, "minecraft:arrow", 1);

        return new Entry(id, spell, title, description, null, Book.WAR_ARCHER);
    }
    public static final Entry scorched_earth = add(scorched_earth());
    private static Entry scorched_earth() {
        var id = Identifier.of(MOD_ID, "scorched_earth");
        var spell = SpellBuilder.createSpellActive();
        var title = "Scorched Earth";
        var description = "Fires a bolt that ignites a line of ground in front of you, dealing {damage} damage.";
        spell.school = MoreSpellSchools.FIRE_RANGED;
        spell.range = 0;
        spell.tier = 4;
        spell.group = EXPLOSIVES;

        spell.active.cast.duration = 0.5F;
        spell.active.cast.animation = PlayerAnimation.of("more_rpg_classes:archery_downwards_pull");
        spell.active.cast.sound = new Sound("archers:bow_pull");

        spell.release.animation = PlayerAnimation.of("more_rpg_classes:archery_downwards_release");
        spell.release.sound = new Sound("minecraft:item.crossbow.shoot");
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.flame.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        5, 0.1F, 0.2F)
        };

        spell.deliver.type = Spell.Delivery.Type.CLOUD;
        var cloud = new Spell.Delivery.Cloud();
        cloud.spawn.sound = new Sound(Sounds.SCORCHED_EARTH_IGNITE.id());
        cloud.volume.radius = 2F;
        cloud.volume.area.vertical_range_multiplier = 0.75F;
        cloud.volume.area = new Spell.Target.Area();
        cloud.impact_tick_interval = 5;
        cloud.time_to_live_seconds = 1.5F;
        cloud.client_data = new Spell.Delivery.Cloud.ClientData();
        cloud.client_data.light_level = 15;
        cloud.client_data.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_b.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        10, 0.1F, 0.3F),
                new ParticleBatch(
                        "smoke",
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.FEET,
                        3, 0.1F, 0.2F)
        };
        cloud.placement = new Spell.EntityPlacement();
        cloud.placement.force_onto_ground = true;
        cloud.placement.location_offset_by_look = 2.0F;

        var additionalPlacements = new ArrayList<Spell.EntityPlacement>();
        var stepBlocks = 3.0F;
        var stepDelayTicks = 2;
        var steps = 16;
        for (var i = 1; i <= steps; i++) {
            var placement = new Spell.EntityPlacement();
            placement.force_onto_ground = true;
            placement.location_offset_by_look = 2.0F + stepBlocks * i;
            placement.delay_ticks = stepDelayTicks;
            additionalPlacements.add(placement);
        }
        cloud.additional_placements = additionalPlacements;

        spell.deliver.clouds = List.of(cloud);

        var damage = SpellBuilder.Impacts.damage(0.5F, 0F);
        damage.sound = new Sound("entity.generic.burn");

        var fire = SpellBuilder.Impacts.fire(3);
        fire.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_b.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        15, 0.1F, 0.3F)
        };

        spell.impacts = List.of(damage, fire);

        SpellBuilder.Cost.cooldown(spell, 30);
        SpellBuilder.Cost.exhaust(spell, 0.35F);

        return new Entry(id, spell, title, description, null, Book.WAR_ARCHER);
    }
    public static final Entry explosive_barrel = add(explosive_barrel());
    private static Entry explosive_barrel() {
        var id = Identifier.of(MOD_ID, "explosive_barrel");
        var spell = SpellBuilder.createSpellActive();
        var title = "Explosive Barrel";
        var description = "Places an explosive barrel that detonates when struck or approached by an enemy, creating a huge explosion dealing {explosion_damage} damage and setting enemies on fire.";
        spell.school = MoreSpellSchools.FIRE_RANGED;
        spell.range = 0;
        spell.tier = 3;
        spell.secondary_archetype = Spell.ExtendedArchetype.ANY;
        spell.group = EXPLOSIVES;

        spell.release.animation = PlayerAnimation.of("more_rpg_classes:place_object_instant");
        spell.release.sound = new Sound("minecraft:block.barrel.open");
        spell.release.particles = new ParticleBatch[]{
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.GROUND,
                        20, 0.2F, 0.4F)
        };

        var spawn = new Spell.Impact();
        spawn.action = new Spell.Impact.Action();
        spawn.action.type = Spell.Impact.Action.Type.SPAWN;
        var barrel = new Spell.Impact.Action.Spawn();
        barrel.entity_type_id = ExplosiveBarrelEntity.ENTITY_TYPE.getRegistryEntry().getKey().get().getValue().toString();
        barrel.time_to_live_seconds = 25;
        barrel.placement.location_offset_by_look = 2.0F;
        barrel.placement.force_onto_ground = true;
        spawn.action.spawns = List.of(barrel);

        spell.impacts = List.of(spawn);

        SpellBuilder.Cost.cooldown(spell, 8);
        SpellBuilder.Cost.exhaust(spell, 0.3F);

        var mutator = helperDamageMutator(Identifier.of(MOD_ID, "explosive_barrel_explosion"), "{explosion_damage}");
        return new Entry(id, spell, title, description, mutator, Book.WAR_ARCHER);
    }
    public static final Entry EXPLOSIVE_BARREL_EXPLOSION = add(EXPLOSIVE_BARREL_EXPLOSION());
    private static Entry EXPLOSIVE_BARREL_EXPLOSION() {
        var id = Identifier.of(MOD_ID, "explosive_barrel_explosion");
        var title = "";
        var description = "";

        var spell = SpellBuilder.createSpellActive();
        spell.school = MoreSpellSchools.FIRE_RANGED;
        spell.range = 6;
        spell.tier = 4;

        spell.target.type = Spell.Target.Type.AREA;
        spell.target.area = new Spell.Target.Area();
        spell.target.area.vertical_range_multiplier = 2.0F;

        spell.release = new Spell.Release();
        spell.release.sound = new Sound(Sounds.BARREL_EXPLOSION.id());
        spell.release.particles = new ParticleBatch[] {
                new ParticleBatch(
                        SpellEngineParticles.fire_explosion.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        1, 0.2F, 0.6F).scale(1.5F),
                new ParticleBatch(
                        SpellEngineParticles.smoke_large.id().toString(),
                        ParticleBatch.Shape.CIRCLE, ParticleBatch.Origin.GROUND,
                        40, 0.5F, 0.9F),
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_b.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.GROUND,
                        40, 0.5F, 0.9F),
                new ParticleBatch(
                        SpellEngineParticles.area_effect_574.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        1, 0, 0)
                        .scale(3.0F)
                        .color(Color.RED.toRGBA()),
                new ParticleBatch(
                        SpellEngineParticles.aura_effect_574.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        1, 0, 0)
                        .scale(3.0F)
                        .color(Color.RED.toRGBA()),
        };

        var damage = SpellBuilder.Impacts.damage(0.3F, 1.5F);
        damage.particles = new ParticleBatch[] {
                new ParticleBatch(
                        SpellEngineParticles.flame_medium_a.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        30, 0.2F, 0.7F),
                new ParticleBatch(
                        SpellEngineParticles.smoke_medium.id().toString(),
                        ParticleBatch.Shape.SPHERE, ParticleBatch.Origin.CENTER,
                        30, 0.2F, 0.7F)
        };
        var fire = SpellBuilder.Impacts.fire(2);

        spell.impacts = List.of(damage, fire);

        SpellBuilder.Cost.cooldown(spell, 1);

        return new Entry(id, spell, title, description, null, null);
    }
}

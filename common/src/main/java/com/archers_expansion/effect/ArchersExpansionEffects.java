package com.archers_expansion.effect;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.spell_engine.rpg_series.config.AttributeModifier;
import net.spell_engine.rpg_series.config.ConfigFile;
import net.spell_engine.rpg_series.config.EffectConfig;
import net.spell_engine.api.effect.*;
import net.spell_engine.api.entity.SpellEngineAttributes;
import net.spell_engine.api.event.CombatEvents;
import net.spell_engine.api.spell.event.SpellEvents;
import net.spell_power.api.SpellSchools;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ArchersExpansionEffects {
    private static String attributeId(EntityAttribute attribute) {
        return Registries.ATTRIBUTE.getId(attribute).toString();
    }

    public static final List<Effects.Entry> entries = new ArrayList<>();
    private static Effects.Entry add(Effects.Entry entry) {
        entries.add(entry);
        return entry;
    }

    public static final Effects.Entry FAST_SHOT = add(new Effects.Entry(
            new Identifier(MOD_ID, "fast_shot"),
            "Fast Shot",
            "Increases Range Haste with each shot.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            EntityAttributes_RangedWeapon.HASTE.id.toString(),
                            0.025F,
                            EntityAttributeModifier.Operation.MULTIPLY_BASE
                    )
            ))
    ));

    public static final Effects.Entry CHOKING_GAS = add(new Effects.Entry(
            new Identifier(MOD_ID, "choking_gas"),
            "Choking Gas Cloud",
            "Damages the target through poison and silences it. Damage is increased on bleeding entities.",
            new ChokingGasEffect(StatusEffectCategory.HARMFUL, 0x805e4d),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            SpellEngineAttributes.HEALING_TAKEN.id.toString(),
                            -0.50F,
                            EntityAttributeModifier.Operation.MULTIPLY_BASE
                    )
            ))
    ));

    public static final Effects.Entry DISABLING_SHOT = add(new Effects.Entry(
            new Identifier(MOD_ID, "disabling_shot"),
            "Disabling Shot",
            "Slows the target and damages it overtime.",
            new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x805e4d),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            attributeId(EntityAttributes.GENERIC_MOVEMENT_SPEED),
                            -0.25F,
                            EntityAttributeModifier.Operation.MULTIPLY_BASE
                    )
            ))
    ));

    public static final Effects.Entry INFILTRATORS_VANISH = add(new Effects.Entry(
            new Identifier(MOD_ID, "infiltrators_vanish"),
            "Infiltrator Vanish",
            "Invisible to enemies",
            new InfiltratorsVanishEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            attributeId(EntityAttributes.GENERIC_MOVEMENT_SPEED),
                            -0.5F,
                            EntityAttributeModifier.Operation.MULTIPLY_BASE
                    )
            ))
    ));

    public static final Effects.Entry INFILTRATORS_SPEED = add(new Effects.Entry(
            new Identifier(MOD_ID, "infiltrators_speed"),
            "Infiltrator Speed",
            "Faster movement in stealth",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            attributeId(EntityAttributes.GENERIC_MOVEMENT_SPEED),
                            0.5F,
                            EntityAttributeModifier.Operation.MULTIPLY_BASE
                    )
            ))
    ));

    public static final Effects.Entry FROZEN_SHOT = add(new Effects.Entry(
            new Identifier(MOD_ID, "frozen_shot"),
            "Frozen Shot",
            "Your next shots stack the frosted effect on targets.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x99ccff),
            new EffectConfig(List.of())
    ));

    public static final Effects.Entry ENCHANTED_CRYSTAL_ARROW = add(new Effects.Entry(
            new Identifier(MOD_ID, "enchanted_crystal_arrow"),
            "Crystal Arrow",
            "Stuns the target and gives the target frozen ticks.",
            new CrystalArrowEffect(StatusEffectCategory.HARMFUL, 0x99ccff),
            new EffectConfig(List.of())
    ));

    public static final Effects.Entry FROZEN_PACT = add(new Effects.Entry(
            new Identifier(MOD_ID, "frozen_pact"),
            "Frozen Pact",
            "Damages the targets according to their Frozen ticks and reduces their offensive attributes.",
            new FrozenPactEffect(StatusEffectCategory.HARMFUL, 0x99ccff),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            attributeId(EntityAttributes.GENERIC_ATTACK_DAMAGE),
                            -0.2F,
                            EntityAttributeModifier.Operation.MULTIPLY_BASE
                    ),
                    new AttributeModifier(
                            SpellSchools.GENERIC.id.toString(),
                            -0.2F,
                            EntityAttributeModifier.Operation.MULTIPLY_BASE
                    ),
                    new AttributeModifier(
                            EntityAttributes_RangedWeapon.DAMAGE.id.toString(),
                            -0.2F,
                            EntityAttributeModifier.Operation.MULTIPLY_BASE
                    )
            ))
    ));

    public static final Effects.Entry FROZEN_FUSILLADE_SLOW = add(new Effects.Entry(
            new Identifier(MOD_ID, "frozen_fusillade_slow"),
            "Frozen Fusillade",
            "Movement is slowed by a barrage of frozen shards.",
            new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x99ccff),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            attributeId(EntityAttributes.GENERIC_MOVEMENT_SPEED),
                            -0.3F,
                            EntityAttributeModifier.Operation.MULTIPLY_BASE
                    )
            ))
    ));

    public static final Effects.Entry FROZEN_FUSILLADE_HASTE = add(new Effects.Entry(
            new Identifier(MOD_ID, "frozen_fusillade_haste"),
            "Frozen Fusillade",
            "Movement is quickened by a barrage of frozen shards.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x99ccff),
            new EffectConfig(List.of(
                    new AttributeModifier(
                            attributeId(EntityAttributes.GENERIC_MOVEMENT_SPEED),
                            0.3F,
                            EntityAttributeModifier.Operation.MULTIPLY_BASE
                    )
            ))
    ));

    public static final Effects.Entry SMOLDERING_ARROWS = add(new Effects.Entry(
            new Identifier(MOD_ID, "smoldering_arrows"),
            "Smoldering Arrow",
            "Your next shot creates a small explosion near the target, damaging and burning entities around.",
            new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d),
            new EffectConfig(List.of())
    ));

    public static StatusEffect getEntry(Effects.Entry entry) {
        return entry.effect;
    }

    public static void register(ConfigFile.Effects config) {
        effectsToRegister(config).forEach((id, effect) -> Registry.register(Registries.STATUS_EFFECT, id, effect));
        Effects.linkEntries(entries);
        installBehaviours();
    }

    public static Map<Identifier, StatusEffect> effectsToRegister(ConfigFile.Effects config) {
        for (var entry : entries) {
            Synchronized.configure(entry.effect, true);
        }

        ActionImpairing.configure(CHOKING_GAS.effect, EntityActionsAllowed.SILENCE);
        ActionImpairing.configure(ENCHANTED_CRYSTAL_ARROW.effect, EntityActionsAllowed.STUN);

        return Effects.effectsToRegister(entries, config.effects);
    }

    public static void installBehaviours() {
        CombatEvents.ENTITY_ANY_ATTACK.register((args) -> {
            var attacker = args.attacker();
            var entry = getEntry(INFILTRATORS_VANISH);
            if (attacker.hasStatusEffect(entry)) {
                attacker.removeStatusEffect(entry);
            }
        });

        var vanishId = new Identifier(MOD_ID, "infiltrators_arrow");
        var alterEgoId = new Identifier(MOD_ID, "alter_ego");
        var alterEgoExplosionId = new Identifier(MOD_ID, "alter_ego_explosion");
        SpellEvents.SPELL_CAST.register((args) -> {
            var caster = args.caster();
            var spellId = args.spell().getKey().get().getValue();
            var entry = getEntry(INFILTRATORS_VANISH);
            if (caster.hasStatusEffect(entry)
                    && !spellId.equals(vanishId) && !spellId.equals(alterEgoId) && !spellId.equals(alterEgoExplosionId)) {
                caster.removeStatusEffect(entry);
            }
        });

        CombatEvents.ITEM_USE.register((args) -> {
            var user = args.user();
            var entry = getEntry(INFILTRATORS_VANISH);
            if (user.hasStatusEffect(entry)) {
                user.removeStatusEffect(entry);
            }
        });


        OnRemoval.configure(INFILTRATORS_VANISH.effect, (context) -> {
            ((InfiltratorsVanishEffect) INFILTRATORS_VANISH.effect).onStealthRemoved(context.entity());
            var speedEntry = getEntry(INFILTRATORS_SPEED);
            if (context.entity().hasStatusEffect(speedEntry)) {
                context.entity().removeStatusEffect(speedEntry);
            }
        });
    }
}

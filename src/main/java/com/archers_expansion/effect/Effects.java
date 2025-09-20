package com.archers_expansion.effect;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.effect.*;
import net.spell_engine.api.entity.SpellEngineAttributes;
import net.spell_engine.api.event.CombatEvents;
import net.spell_engine.api.spell.event.SpellEvents;

import java.util.ArrayList;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;
import static com.archers_expansion.ArchersExpansionMod.effectsConfig;

public class Effects {
    private static final ArrayList<Entry> entries = new ArrayList<>();
    public static class Entry {
        public final Identifier id;
        public final StatusEffect effect;
        public RegistryEntry<StatusEffect> registryEntry;
        public Entry(String name, StatusEffect effect) {
            this.id = Identifier.of(MOD_ID, name);
            this.effect = effect;
            entries.add(this);
        }
        public void register() {
            registryEntry = Registry.registerReference(Registries.STATUS_EFFECT, id, effect);
        }
        public Identifier modifierId() {
            return Identifier.of(MOD_ID, "effect." + id.getPath());
        }
    }


    //DEAD EYE
    public static final Entry FAST_SHOT  =  new Entry("fast_shot",new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d));
    public static final Entry CHOKING_GAS = new Entry("choking_gas" , new ChokingGasEffect(StatusEffectCategory.HARMFUL, 0x805e4d));
    public static final Entry DISABLING_SHOT = new Entry("disabling_shot", new DisablingShotEffect(StatusEffectCategory.HARMFUL, 0x805e4d));
    public static final Entry INFILTRATORS_ARROW = new Entry("infiltrators_arrow", new InfiltratorsArrowEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d));
    public static final Entry INFILTRATORS_SPEED = new Entry("infiltrators_speed", new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d));

    //TUNDRA HUNTER
    public static final Entry FROZEN_SHOT = new Entry("frozen_shot",new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x99ccff));
    public static final Entry ENCHANTED_CRSYSTAL_ARROW = new Entry("enchanted_crystal_arrow",new CrystalArrowEffect(StatusEffectCategory.HARMFUL, 0x99ccff));
    public static final Entry FROZEN_PACT = new Entry("frozen_pact",new FrozenPactEffect(StatusEffectCategory.HARMFUL, 0x99ccff));
    public static final Entry WINTERS_GRASP  = new Entry("winters_grip", new WintersGraspEffect(StatusEffectCategory.HARMFUL, 0x805e4d));

    //WAR ARCHER
    public static final Entry SMOLDERING_ARROWS = new Entry("smoldering_arrows",new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d));
    public static final Entry PIN_DOWN = new Entry("pin_down",new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x805e4d));

    public static void register (){
        var config = effectsConfig.value;

        FAST_SHOT.effect.
                addAttributeModifier(EntityAttributes_RangedWeapon.HASTE.entry, FAST_SHOT.modifierId(),
                effectsConfig.value.fast_shot_haste_increase_per_stack, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        DISABLING_SHOT.effect.
                addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,DISABLING_SHOT.modifierId(),
                effectsConfig.value.disabling_shot_decreased_movement_speed, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        FROZEN_PACT.effect.
                addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,FROZEN_PACT.modifierId(),
                effectsConfig.value.frozen_pact_decreased_attack, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        PIN_DOWN.effect.
                addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,PIN_DOWN.modifierId(),
                -10.00, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL)
                .addAttributeModifier(
                        EntityAttributes.GENERIC_JUMP_STRENGTH, PIN_DOWN.modifierId(),
                        -10.00, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        CHOKING_GAS.effect.
                addAttributeModifier(SpellEngineAttributes.HEALING_TAKEN.entry,CHOKING_GAS.modifierId(),
                        effectsConfig.value.choking_gas_healing_taken, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        INFILTRATORS_ARROW.effect.
                addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, INFILTRATORS_ARROW.modifierId(),
                config.stealth_movement_speed_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        INFILTRATORS_SPEED.effect.
                addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, INFILTRATORS_SPEED.modifierId(),
                        0.5F, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);
        WINTERS_GRASP.effect.
                addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, WINTERS_GRASP.modifierId(),
                config.winters_grasp_movement_speed_multiplier, EntityAttributeModifier.Operation.ADD_MULTIPLIED_BASE);


        CombatEvents.ENTITY_ANY_ATTACK.register((args) -> {
            var attacker = args.attacker();
            if (attacker.hasStatusEffect(INFILTRATORS_ARROW.registryEntry)) {
                attacker.removeStatusEffect(INFILTRATORS_ARROW.registryEntry);
            }
        });
        var vanishId = Identifier.of(MOD_ID, "infiltrators_arrow");
        SpellEvents.SPELL_CAST.register((args) -> {
            var caster = args.caster();
            var spellId = args.spell().getKey().get().getValue();
            if (caster.hasStatusEffect(INFILTRATORS_ARROW.registryEntry)&& !spellId.equals(vanishId)) {
                caster.removeStatusEffect(INFILTRATORS_ARROW.registryEntry);
            }
        });
        CombatEvents.ITEM_USE.register((args) -> {
            var user = args.user();
            if (user.hasStatusEffect(INFILTRATORS_ARROW.registryEntry)) {
                user.removeStatusEffect(INFILTRATORS_ARROW.registryEntry);
            }
        });
        OnRemoval.configure(INFILTRATORS_ARROW.effect, (context) -> {
            InfiltratorsArrowEffect.onRemove(context.entity());
            if (context.entity().hasStatusEffect(INFILTRATORS_SPEED.registryEntry)) {
                context.entity().removeStatusEffect(INFILTRATORS_SPEED.registryEntry);
            }
        });

        Synchronized.configure(FAST_SHOT.effect,true);
        Synchronized.configure(CHOKING_GAS.effect,true);
        Synchronized.configure(DISABLING_SHOT.effect,true);
        Synchronized.configure(ENCHANTED_CRSYSTAL_ARROW.effect,true);
        Synchronized.configure(FROZEN_PACT.effect,true);
        Synchronized.configure(FROZEN_SHOT.effect,true);
        Synchronized.configure(SMOLDERING_ARROWS.effect,true);
        Synchronized.configure(PIN_DOWN.effect,true);
        Synchronized.configure(INFILTRATORS_ARROW.effect,true);
        Synchronized.configure(INFILTRATORS_SPEED.effect,true);
        Synchronized.configure(WINTERS_GRASP.effect,true);

        ActionImpairing.configure(CHOKING_GAS.effect, EntityActionsAllowed.SILENCE);
        ActionImpairing.configure(ENCHANTED_CRSYSTAL_ARROW.effect, EntityActionsAllowed.STUN);

        for (var entry: entries) {
            entry.register();
        }
    }
}

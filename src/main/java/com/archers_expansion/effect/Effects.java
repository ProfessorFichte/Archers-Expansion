package com.archers_expansion.effect;

import net.fabric_extras.ranged_weapon.api.EntityAttributes_RangedWeapon;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.spell_engine.api.effect.*;
import net.spell_engine.api.event.CombatEvents;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;
import static com.archers_expansion.ArchersExpansionMod.effectsConfig;

public class Effects {

    //DEAD EYE
    public static StatusEffect FAST_SHOT = new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d);
    public static StatusEffect CHOKING_GAS = new ChokingGasEffect(StatusEffectCategory.HARMFUL, 0x805e4d);
    public static StatusEffect LEAPING_SHOT = new LeapingShotEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d);
    public static StatusEffect DISABLING_SHOT = new DisablingShotEffect(StatusEffectCategory.HARMFUL, 0x805e4d);
    public static StatusEffect INFILTRATORS_ARROW = new InfiltratorsArrowEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d);
    //TUNDRA HUNTER
    public static StatusEffect ENCHANTED_CRYSTAL_ARROW = new CrystalArrowEffect(StatusEffectCategory.HARMFUL, 0x99ccff);
    public static StatusEffect FROZEN_PACT = new FrozenPactEffect(StatusEffectCategory.HARMFUL, 0x99ccff);
    public static StatusEffect WINTERS_GRASP = new WintersGraspEffect(StatusEffectCategory.HARMFUL, 0x99ccff);

    //WAR ARCHER
    public static StatusEffect SMOLDERING_ARROW = new SmolderingArrow(StatusEffectCategory.HARMFUL, 0x805e4d);
    public static StatusEffect POINT_BLANK_SHOT = new PointBlankShot(StatusEffectCategory.HARMFUL, 0x805e4d);
    public static StatusEffect PIN_DOWN = new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x805e4d);


    public static void register (){
        var config = effectsConfig.value;

        FAST_SHOT.addAttributeModifier(EntityAttributes_RangedWeapon.HASTE.attribute, "927602d9-28f9-49d7-a7ed-a2eb2f38a58b",
                config.fast_shot_haste_increase_per_stack, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        DISABLING_SHOT.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,"bf290ad8-e59e-4983-b880-7cd56aa10820",
                config.disabling_shot_decreased_movement_speed, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        FROZEN_PACT.addAttributeModifier(EntityAttributes.GENERIC_ATTACK_DAMAGE,"004f5523-4920-42bc-a79c-574e93cf9801",
                config.frozen_pact_decreased_attack, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        PIN_DOWN.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED,"bf290ad8-e59e-4983-b880-7cd56aa10820",
                -1.00, EntityAttributeModifier.Operation.MULTIPLY_TOTAL);
        INFILTRATORS_ARROW.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "bf290ad8-e59e-4983-b880-7cd56aa10820",
                config.stealth_movement_speed_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE);
        WINTERS_GRASP.addAttributeModifier(EntityAttributes.GENERIC_MOVEMENT_SPEED, "bf290ad8-e59e-4983-b880-7cd56aa10820",
                config.winters_grasp_movement_speed_multiplier, EntityAttributeModifier.Operation.MULTIPLY_BASE);


        Synchronized.configure(FAST_SHOT,true);
        Synchronized.configure(CHOKING_GAS,true);
        Synchronized.configure(LEAPING_SHOT,true);
        Synchronized.configure(DISABLING_SHOT,true);
        Synchronized.configure(ENCHANTED_CRYSTAL_ARROW,true);
        Synchronized.configure(FROZEN_PACT,true);
        Synchronized.configure(SMOLDERING_ARROW,true);
        Synchronized.configure(POINT_BLANK_SHOT,true);
        Synchronized.configure(PIN_DOWN,true);
        Synchronized.configure(INFILTRATORS_ARROW,true);
        Synchronized.configure(WINTERS_GRASP,true);

        RemoveOnHit.configure(INFILTRATORS_ARROW, true);

        ActionImpairing.configure(CHOKING_GAS, EntityActionsAllowed.SILENCE);
        ActionImpairing.configure(ENCHANTED_CRYSTAL_ARROW, EntityActionsAllowed.STUN);

        HealthImpacting.configureHealingTaken(CHOKING_GAS,  effectsConfig.value.choking_gas_healing_taken);

        CombatEvents.ENTITY_ATTACK.register((args) -> {
            var attacker = args.attacker();
            if (attacker.hasStatusEffect(INFILTRATORS_ARROW)) {
                attacker.removeStatusEffect(INFILTRATORS_ARROW);
            }
        });
        CombatEvents.SPELL_CAST.register((args) -> {
            var caster = args.caster();
            if (caster.hasStatusEffect(INFILTRATORS_ARROW)) {
                caster.removeStatusEffect(INFILTRATORS_ARROW);
            }
        });
        CombatEvents.ITEM_USE.register((args) -> {
            var user = args.user();
            if (user.hasStatusEffect(INFILTRATORS_ARROW)) {
                user.removeStatusEffect(INFILTRATORS_ARROW);
            }
        });

        int effect_id = 5900;
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "fast_shot").toString(), FAST_SHOT);
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "choking_gas").toString(), CHOKING_GAS);
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "leaping_shot").toString(),LEAPING_SHOT);
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "disabling_shot").toString(),DISABLING_SHOT);
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "enchanted_crystal_arrow").toString(),ENCHANTED_CRYSTAL_ARROW);
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "frozen_pact").toString(),FROZEN_PACT);
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "smoldering_arrow").toString(),SMOLDERING_ARROW);
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "point_blank_shot").toString(),POINT_BLANK_SHOT);
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "pin_down").toString(),PIN_DOWN);
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "infiltrators_arrow").toString(),INFILTRATORS_ARROW);
        Registry.register(Registries.STATUS_EFFECT, effect_id++, new Identifier(MOD_ID, "winters_grip").toString(),WINTERS_GRASP);
    }
}

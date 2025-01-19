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
    public static final Entry LEAPING_SHOT = new Entry("leaping_shot", new LeapingShotEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d));
    public static final Entry DISABLING_SHOT = new Entry("disabling_shot", new DisablingShotEffect(StatusEffectCategory.HARMFUL, 0x805e4d));
    //TUNDRA HUNTER
    public static final Entry FROZEN_SHOT = new Entry("frozen_shot",new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x99ccff));
    public static final Entry ENCHANTED_CRSYSTAL_ARROW = new Entry("enchanted_crystal_arrow",new CrystalArrowEffect(StatusEffectCategory.HARMFUL, 0x99ccff));
    public static final Entry FROZEN_PACT = new Entry("frozen_pact",new FrozenPactEffect(StatusEffectCategory.HARMFUL, 0x99ccff));

    //WAR ARCHER
    public static final Entry SMOLDERING_ARROW = new Entry("smoldering_arrow",new SmolderingArrow(StatusEffectCategory.HARMFUL, 0x805e4d));
    public static final Entry SMOLDERING_ARROWS = new Entry("smoldering_arrows",new CustomStatusEffect(StatusEffectCategory.BENEFICIAL, 0x805e4d));
    public static final Entry POINT_BLANK_SHOT = new Entry("point_blank_shot",new  PointBlankShot(StatusEffectCategory.HARMFUL, 0x805e4d));
    public static final Entry PIN_DOWN = new Entry("pin_down",new CustomStatusEffect(StatusEffectCategory.HARMFUL, 0x805e4d));

    public static void register (){
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
                -1.00, EntityAttributeModifier.Operation.ADD_MULTIPLIED_TOTAL);

        SpellStash.configure(FAST_SHOT.effect, Identifier.of(MOD_ID, "fast_shot"), 0);
        SpellStash.configure(FROZEN_SHOT.effect, Identifier.of(MOD_ID, "frozen_shot"), 0);
        SpellStash.configure(SMOLDERING_ARROWS.effect, Identifier.of(MOD_ID, "smoldering_arrow"), 1);

        Synchronized.configure(FAST_SHOT.effect,true);
        Synchronized.configure(CHOKING_GAS.effect,true);
        Synchronized.configure(LEAPING_SHOT.effect,true);
        Synchronized.configure(DISABLING_SHOT.effect,true);
        Synchronized.configure(ENCHANTED_CRSYSTAL_ARROW.effect,true);
        Synchronized.configure(FROZEN_PACT.effect,true);
        Synchronized.configure(SMOLDERING_ARROW.effect,true);
        Synchronized.configure(POINT_BLANK_SHOT.effect,true);
        Synchronized.configure(PIN_DOWN.effect,true);

        ActionImpairing.configure(CHOKING_GAS.effect, EntityActionsAllowed.SILENCE);
        ActionImpairing.configure(ENCHANTED_CRSYSTAL_ARROW.effect, EntityActionsAllowed.STUN);

        HealthImpacting.configureHealingTaken(CHOKING_GAS.effect,  effectsConfig.value.choking_gas_healing_taken);

        for (var entry: entries) {
            entry.register();
        }
    }
}

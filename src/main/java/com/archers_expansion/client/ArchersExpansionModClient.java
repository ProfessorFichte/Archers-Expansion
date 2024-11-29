package com.archers_expansion.client;

import com.archers_expansion.effect.Effects;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.render.CustomModels;

import java.util.List;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ArchersExpansionModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        CustomModels.registerModelIds(List.of(
                Identifier.of(MOD_ID, "projectile/glacial_arrow"),
                Identifier.of(MOD_ID, "projectile/regular_arrow")
        ));

        CustomParticleStatusEffect.register(Effects.ENCHANTED_CRSYSTAL_ARROW.effect, new CrystalArrowParticles(15));
        CustomParticleStatusEffect.register(Effects.CHOKING_GAS.effect, new ChokingPoisonParticles(5));
        CustomParticleStatusEffect.register(Effects.CHOKING_POISON.effect, new ChokingPoisonParticles(5));
        CustomParticleStatusEffect.register(Effects.PIN_DOWN.effect, new PinDownParticles(2));

    }
}

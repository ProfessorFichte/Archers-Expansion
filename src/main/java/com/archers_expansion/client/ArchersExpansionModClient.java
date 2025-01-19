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
                new Identifier(MOD_ID, "projectile/glacial_arrow"),
                new Identifier(MOD_ID, "projectile/regular_arrow")
        ));

        CustomParticleStatusEffect.register(Effects.ENCHANTED_CRSYSTAL_ARROW, new CrystalArrowParticles(15));
        CustomParticleStatusEffect.register(Effects.CHOKING_GAS, new ChokingPoisonParticles(5));
        CustomParticleStatusEffect.register(Effects.PIN_DOWN, new PinDownParticles(2));

    }
}

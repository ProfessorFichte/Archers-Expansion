package com.archers_expansion.client;

import com.archers_expansion.client.armor.DeadeyeRenderer;
import com.archers_expansion.client.armor.TundraArcherRenderer;
import com.archers_expansion.client.armor.WarArcherRenderer;
import com.archers_expansion.effect.Effects;
import com.archers_expansion.items.armors.Armors;
import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.util.Identifier;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.item.armor.Armor;
import net.spell_engine.api.render.CustomModels;
import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRenderer;

import java.util.List;
import java.util.function.Supplier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ArchersExpansionModClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {

        CustomModels.registerModelIds(List.of(
                Identifier.of(MOD_ID, "projectile/glacial_arrow"),
                Identifier.of(MOD_ID, "projectile/regular_arrow"),
                Identifier.of(MOD_ID, "projectile/smoldering_arrow"),
                Identifier.of(MOD_ID, "projectile/fast_arrow"),
                Identifier.of(MOD_ID, "projectile/choking_gas_arrow"),
                Identifier.of(MOD_ID, "projectile/pin_down_arrow")
        ));

        registerArmorRenderer(Armors.deadeye_t1, DeadeyeRenderer::deadeye);
        registerArmorRenderer(Armors.netherite_deadeye, DeadeyeRenderer::netherite_deadeye);
        registerArmorRenderer(Armors.tundra_hunter_t1, TundraArcherRenderer::tundra_hunter);
        registerArmorRenderer(Armors.netherite_tundra_hunter, TundraArcherRenderer::netherite_tundra_hunter);
        registerArmorRenderer(Armors.war_archer_t1, WarArcherRenderer::war_archer);
        registerArmorRenderer(Armors.netherite_war_archer, WarArcherRenderer::netherite_war_archer);

        CustomParticleStatusEffect.register(Effects.ENCHANTED_CRSYSTAL_ARROW.effect, new CrystalArrowParticles(15));
        CustomParticleStatusEffect.register(Effects.CHOKING_GAS.effect, new ChokingPoisonParticles(5));
        CustomParticleStatusEffect.register(Effects.PIN_DOWN.effect, new PinDownParticles(2));



    }
    private static void registerArmorRenderer(Armor.Set set, Supplier<AzArmorRenderer> armorRendererSupplier) {
        AzArmorRendererRegistry.register(armorRendererSupplier, set.head, set.chest, set.legs, set.feet);
    }
}

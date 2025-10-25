package com.archers_expansion.client;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.client.armor.ArchersExpansionArmorRenderer;
import com.archers_expansion.client.effect.ChokingPoisonParticles;
import com.archers_expansion.client.effect.CrystalArrowParticles;
import com.archers_expansion.client.effect.PinDownParticles;
import com.archers_expansion.client.entity.WintersGripRenderer;
import com.archers_expansion.effect.Effects;
import com.archers_expansion.entity.WintersGripEntity;
import com.archers_expansion.items.armors.Armors;
import com.archers_expansion.items.armors.ArmoryCompat;
import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRendererRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.item.armor.Armor;
import net.spell_engine.api.render.CustomModels;
import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRenderer;

import java.util.List;
import java.util.function.Supplier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ArchersExpansionModClient {

    public static void init() {

        CustomModels.registerModelIds(List.of(
                Identifier.of(MOD_ID, "projectile/glacial_arrow"),
                Identifier.of(MOD_ID, "projectile/regular_arrow"),
                Identifier.of(MOD_ID, "projectile/smoldering_arrow"),
                Identifier.of(MOD_ID, "projectile/fast_arrow"),
                Identifier.of(MOD_ID, "projectile/choking_gas_arrow"),
                Identifier.of(MOD_ID, "projectile/pin_down_arrow"),
                Identifier.of(MOD_ID, "projectile/infiltrators_arrow"),
                WintersGripRenderer.baseId
        ));

        registerArmorRenderer(Armors.deadeye_t1, ArchersExpansionArmorRenderer::deadeye);
        registerArmorRenderer(Armors.netherite_deadeye, ArchersExpansionArmorRenderer::netherite_deadeye);
        registerArmorRenderer(Armors.tundra_hunter_t1, ArchersExpansionArmorRenderer::tundra_hunter);
        registerArmorRenderer(Armors.netherite_tundra_hunter, ArchersExpansionArmorRenderer::netherite_tundra_hunter);
        registerArmorRenderer(Armors.war_archer_t1, ArchersExpansionArmorRenderer::war_archer);
        registerArmorRenderer(Armors.netherite_war_archer, ArchersExpansionArmorRenderer::netherite_war_archer);
        /*
        if (FabricLoader.getInstance().isModLoaded("armory_rpgs") || ArchersExpansionMod.tweaksConfig.value.ignore_items_required_mods) {
            registerArmorRenderer(ArmoryCompat.bounty_hunter.armorSet(), ArchersExpansionArmorRenderer::bounty_hunter);
            registerArmorRenderer(ArmoryCompat.polar_stalker.armorSet(), ArchersExpansionArmorRenderer::polar_stalker);
            registerArmorRenderer(ArmoryCompat.sentinel_archer.armorSet(), ArchersExpansionArmorRenderer::sentinel_archer);
        }
         */

        CustomParticleStatusEffect.register(Effects.ENCHANTED_CRSYSTAL_ARROW.effect, new CrystalArrowParticles(15));
        CustomParticleStatusEffect.register(Effects.CHOKING_GAS.effect, new ChokingPoisonParticles(10));
        CustomParticleStatusEffect.register(Effects.PIN_DOWN.effect, new PinDownParticles(2));

        EntityRendererRegistry.register(WintersGripEntity.ENTITY_TYPE, WintersGripRenderer::new);

    }
    private static void registerArmorRenderer(Armor.Set set, Supplier<AzArmorRenderer> armorRendererSupplier) {
        AzArmorRendererRegistry.register(armorRendererSupplier, set.head, set.chest, set.legs, set.feet);
    }
}

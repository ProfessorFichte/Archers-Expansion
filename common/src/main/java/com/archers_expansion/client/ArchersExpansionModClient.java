package com.archers_expansion.client;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.client.armor.ArchersExpansionArmorRenderer;
import com.archers_expansion.client.effect.ChokingPoisonParticles;
import com.archers_expansion.client.effect.CrystalArrowParticles;
import com.archers_expansion.client.effect.PinDownParticles;
import com.archers_expansion.client.entity.WintersGripRenderer;
import com.archers_expansion.client.entity.ExplosiveBarrelRenderer;
import com.archers_expansion.client.entity.AlterEgoRenderer;
import com.archers_expansion.client.entity.SpellPolarBearRenderer;
import com.archers_expansion.effect.ArchersEffects;
import com.archers_expansion.entity.WintersGripEntity;
import com.archers_expansion.entity.ExplosiveBarrelEntity;
import com.archers_expansion.entity.AlterEgoEntity;
import com.archers_expansion.entity.PolarBearEntity;
import com.archers_expansion.items.Armors;
import mod.azure.azurelibarmor.common.render.armor.AzArmorRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.rpg_series.item.Armor;
import mod.azure.azurelibarmor.common.render.armor.AzArmorRenderer;

import java.util.function.Supplier;

public class ArchersExpansionModClient {

    public static void init() {

        registerArmorRenderer(Armors.deadeye_t1.armorSet(), ArchersExpansionArmorRenderer::deadeye);
        registerArmorRenderer(Armors.netherite_deadeye.armorSet(), ArchersExpansionArmorRenderer::netherite_deadeye);
        registerArmorRenderer(Armors.tundra_hunter_t1.armorSet(), ArchersExpansionArmorRenderer::tundra_hunter);
        registerArmorRenderer(Armors.netherite_tundra_hunter.armorSet(), ArchersExpansionArmorRenderer::netherite_tundra_hunter);
        registerArmorRenderer(Armors.war_archer_t1.armorSet(), ArchersExpansionArmorRenderer::war_archer);
        registerArmorRenderer(Armors.netherite_war_archer.armorSet(), ArchersExpansionArmorRenderer::netherite_war_archer);

        if (FabricLoader.getInstance().isModLoaded("armory_rpgs") || ArchersExpansionMod.tweaksConfig.value.ignore_items_required_mods) {
            registerArmorRenderer(Armors.bountyHunterArmorSet.armorSet(), ArchersExpansionArmorRenderer::bounty_hunter);
            registerArmorRenderer(Armors.polarStalkerArmorSet.armorSet(), ArchersExpansionArmorRenderer::polar_stalker);
            registerArmorRenderer(Armors.sentinelArcherArmorSet.armorSet(), ArchersExpansionArmorRenderer::sentinel_archer);
        }

        CustomParticleStatusEffect.register(ArchersEffects.ENCHANTED_CRYSTAL_ARROW.effect, new CrystalArrowParticles(15));
        CustomParticleStatusEffect.register(ArchersEffects.CHOKING_GAS.effect, new ChokingPoisonParticles(10));
        CustomParticleStatusEffect.register(ArchersEffects.PIN_DOWN.effect, new PinDownParticles(2));

        EntityRendererRegistry.register(WintersGripEntity.ENTITY_TYPE, WintersGripRenderer::new);
        EntityRendererRegistry.register(ExplosiveBarrelEntity.ENTITY_TYPE, ExplosiveBarrelRenderer::new);
        EntityRendererRegistry.register(AlterEgoEntity.ENTITY_TYPE, AlterEgoRenderer::new);
        EntityRendererRegistry.register(PolarBearEntity.ENTITY_TYPE, SpellPolarBearRenderer::new);

    }
    private static void registerArmorRenderer(Armor.Set set, Supplier<AzArmorRenderer> armorRendererSupplier) {
        AzArmorRendererRegistry.register(armorRendererSupplier, set.head, set.chest, set.legs, set.feet);
    }
}

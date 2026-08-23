package com.archers_expansion.client;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.client.armor.ArchersExpansionArmorRenderer;
import com.archers_expansion.client.effect.ChokingPoisonParticles;
import com.archers_expansion.client.effect.CrystalArrowParticles;
import com.archers_expansion.client.entity.*;
import com.archers_expansion.effect.ArchersExpansionEffects;
import com.archers_expansion.entity.*;
import com.archers_expansion.items.Armors;
import com.archers_expansion.spell.ArchersExpansionSpells;
import mod.azure.azurelibarmor.common.render.armor.AzArmorRendererRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityModelLayerRegistry;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.fabricmc.loader.api.FabricLoader;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.client.render.SpellCloudRenderer;
import net.spell_engine.rpg_series.item.Armor;
import mod.azure.azurelibarmor.common.render.armor.AzArmorRenderer;

import java.util.function.Supplier;

public class ArchersExpansionModClient {

    public static void init() {

        ArchersExpansionSpells.registerTooltipTokens();

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

        CustomParticleStatusEffect.register(ArchersExpansionEffects.ENCHANTED_CRYSTAL_ARROW.effect, new CrystalArrowParticles(15));
        CustomParticleStatusEffect.register(ArchersExpansionEffects.CHOKING_GAS.effect, new ChokingPoisonParticles(10));

        EntityModelLayerRegistry.registerModelLayer(GlacialBearEntityModel.LAYER_LOCATION, GlacialBearEntityModel::createBodyLayer);

        EntityRendererRegistry.register(ExplosiveBarrelEntity.ENTITY_TYPE, ExplosiveBarrelRenderer::new);
        EntityRendererRegistry.register(AlterEgoEntity.ENTITY_TYPE, AlterEgoRenderer::new);
        EntityRendererRegistry.register(PolarBearEntity.ENTITY_TYPE, SpellPolarBearRenderer::new);
        EntityRendererRegistry.register(PoisonFlaskProjectile.ENTITY_TYPE, PoisonFlaskRenderer::new);
        EntityRendererRegistry.register(FrozenFussiladeEntity.ENTITY_TYPE, SpellCloudRenderer::new);

    }
    private static void registerArmorRenderer(Armor.Set set, Supplier<AzArmorRenderer> armorRendererSupplier) {
        AzArmorRendererRegistry.register(armorRendererSupplier, set.head, set.chest, set.legs, set.feet);
    }
}

package com.archers_expansion.client;

import com.archers_expansion.ArchersExpansionMod;
import com.archers_expansion.client.armor.ArchersExpansionArmorRenderer;
import com.archers_expansion.client.effect.ChokingPoisonParticles;
import com.archers_expansion.client.effect.CrystalArrowParticles;
import com.archers_expansion.effect.ArchersExpansionEffects;
import com.archers_expansion.items.Armors;
import com.archers_expansion.spell.ArchersExpansionSpells;
import net.spell_engine.Platform;
import net.rpg_foundation.armor_api.client.ArmorRenderers;
import net.rpg_foundation.armor_api.client.GeoArmorRenderer;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.rpg_series.item.Armor;

public class ArchersExpansionModClient {

    public static void init() {

        ArchersExpansionSpells.registerTooltipTokens();

        registerArmorRenderer(Armors.deadeye_t1.armorSet(), ArchersExpansionArmorRenderer.deadeye());
        registerArmorRenderer(Armors.netherite_deadeye.armorSet(), ArchersExpansionArmorRenderer.netherite_deadeye());
        registerArmorRenderer(Armors.tundra_hunter_t1.armorSet(), ArchersExpansionArmorRenderer.tundra_hunter());
        registerArmorRenderer(Armors.netherite_tundra_hunter.armorSet(), ArchersExpansionArmorRenderer.netherite_tundra_hunter());
        registerArmorRenderer(Armors.war_archer_t1.armorSet(), ArchersExpansionArmorRenderer.war_archer());
        registerArmorRenderer(Armors.netherite_war_archer.armorSet(), ArchersExpansionArmorRenderer.netherite_war_archer());

        if (Platform.util().isModLoaded("armory_rpgs") || ArchersExpansionMod.tweaksConfig.value.ignore_items_required_mods) {
            registerArmorRenderer(Armors.bountyHunterArmorSet.armorSet(), ArchersExpansionArmorRenderer.bounty_hunter());
            registerArmorRenderer(Armors.polarStalkerArmorSet.armorSet(), ArchersExpansionArmorRenderer.polar_stalker());
            registerArmorRenderer(Armors.sentinelArcherArmorSet.armorSet(), ArchersExpansionArmorRenderer.sentinel_archer());
        }

        CustomParticleStatusEffect.register(ArchersExpansionEffects.ENCHANTED_CRYSTAL_ARROW.effect, new CrystalArrowParticles(15));
        CustomParticleStatusEffect.register(ArchersExpansionEffects.CHOKING_GAS.effect, new ChokingPoisonParticles(10));
    }

    private static void registerArmorRenderer(Armor.Set set, GeoArmorRenderer renderer) {
        ArmorRenderers.register(renderer, set.head, set.chest, set.legs, set.feet);
    }
}

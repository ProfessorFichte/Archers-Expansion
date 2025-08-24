package com.archers_expansion.client.armor;

import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRendererConfig;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class ArchersExpansionArmorRenderer extends AzArmorRenderer {

    public static ArchersExpansionArmorRenderer deadeye() {
        return new ArchersExpansionArmorRenderer("deadeye", "deadeye");
    }
    public static ArchersExpansionArmorRenderer tundra_hunter() {
        return new ArchersExpansionArmorRenderer("tundra_hunter", "tundra_hunter");
    }
    public static ArchersExpansionArmorRenderer war_archer() {
        return new ArchersExpansionArmorRenderer("war_archer", "war_archer");
    }

    public static ArchersExpansionArmorRenderer netherite_deadeye() {
        return new ArchersExpansionArmorRenderer("deadeye", "deadeye");
    }
    public static ArchersExpansionArmorRenderer netherite_tundra_hunter() {
        return new ArchersExpansionArmorRenderer("tundra_hunter", "netherite_tundra_hunter");
    }
    public static ArchersExpansionArmorRenderer netherite_war_archer() {
        return new ArchersExpansionArmorRenderer("war_archer", "netherite_war_archer");
    }

    public static ArchersExpansionArmorRenderer bounty_hunter() {
        return new ArchersExpansionArmorRenderer("bounty_hunter", "bounty_hunter");
    }
    public static ArchersExpansionArmorRenderer polar_stalker() {
        return new ArchersExpansionArmorRenderer("polar_stalker", "polar_stalker");
    }
    public static ArchersExpansionArmorRenderer sentinel_archer() {
        return new ArchersExpansionArmorRenderer("sentinel_archer", "sentinel_archer");
    }

    public ArchersExpansionArmorRenderer(String modelName, String textureName) {
        super(AzArmorRendererConfig.builder(
                Identifier.of(MOD_ID, "geo/" + modelName + ".geo.json"),
                Identifier.of(MOD_ID, "textures/armor/" + textureName + ".png")
        ).build());
    }
}

package com.archers_expansion.client.armor;

import net.minecraft.util.Identifier;
import net.rpg_foundation.armor_api.client.GeoArmorRenderer;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public final class ArchersExpansionArmorRenderer {

    private ArchersExpansionArmorRenderer() { }

    public static GeoArmorRenderer deadeye() {
        return make("deadeye", "deadeye");
    }
    public static GeoArmorRenderer tundra_hunter() {
        return make("tundra_hunter", "tundra_hunter");
    }
    public static GeoArmorRenderer war_archer() {
        return make("war_archer", "war_archer");
    }

    public static GeoArmorRenderer netherite_deadeye() {
        return make("deadeye", "netherite_deadeye");
    }
    public static GeoArmorRenderer netherite_tundra_hunter() {
        return make("tundra_hunter", "netherite_tundra_hunter");
    }
    public static GeoArmorRenderer netherite_war_archer() {
        return make("war_archer", "netherite_war_archer");
    }

    public static GeoArmorRenderer bounty_hunter() {
        return make("bounty_hunter", "bounty_hunter");
    }
    public static GeoArmorRenderer polar_stalker() {
        return make("polar_stalker", "polar_stalker");
    }
    public static GeoArmorRenderer sentinel_archer() {
        return make("sentinel_archer", "sentinel_archer");
    }

    private static GeoArmorRenderer make(String modelName, String textureName) {
        return GeoArmorRenderer.of(
                Identifier.of(MOD_ID, "geo/" + modelName + ".geo.json"),
                Identifier.of(MOD_ID, "textures/armor/" + textureName + ".png"));
    }
}

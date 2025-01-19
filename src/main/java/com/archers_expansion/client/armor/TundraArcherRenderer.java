package com.archers_expansion.client.armor;

import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRendererConfig;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class TundraArcherRenderer extends AzArmorRenderer {
    public static TundraArcherRenderer tundra_hunter() {
        return new TundraArcherRenderer("tundra_hunter", "tundra_hunter");
    }
    public static TundraArcherRenderer netherite_tundra_hunter() {
        return new TundraArcherRenderer("tundra_hunter", "netherite_tundra_hunter");
    }

    public TundraArcherRenderer(String modelName, String textureName) {
        super(AzArmorRendererConfig.builder(
                Identifier.of(MOD_ID, "geo/" + modelName + ".geo.json"),
                Identifier.of(MOD_ID, "textures/armor/" + textureName + ".png")
        ).build());
    }
}

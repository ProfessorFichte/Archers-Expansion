package com.archers_expansion.client.armor;

import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRendererConfig;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class WarArcherRenderer extends AzArmorRenderer {
    public static WarArcherRenderer war_archer() {
        return new WarArcherRenderer("war_archer", "war_archer");
    }
    public static WarArcherRenderer netherite_war_archer() {
        return new WarArcherRenderer("war_archer", "netherite_war_archer");
    }

    public WarArcherRenderer(String modelName, String textureName) {
        super(AzArmorRendererConfig.builder(
                Identifier.of(MOD_ID, "geo/" + modelName + ".geo.json"),
                Identifier.of(MOD_ID, "textures/armor/" + textureName + ".png")
        ).build());
    }
}

package com.archers_expansion.client.armor;

import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRenderer;
import mod.azure.azurelibarmor.rewrite.render.armor.AzArmorRendererConfig;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class DeadeyeRenderer extends AzArmorRenderer {
    public static DeadeyeRenderer deadeye() {
        return new DeadeyeRenderer("deadeye", "deadeye");
    }
    public static DeadeyeRenderer netherite_deadeye() {
        return new DeadeyeRenderer("deadeye", "netherite_deadeye");
    }

    public DeadeyeRenderer(String modelName, String textureName) {
        super(AzArmorRendererConfig.builder(
                Identifier.of(MOD_ID, "geo/" + modelName + ".geo.json"),
                Identifier.of(MOD_ID, "textures/armor/" + textureName + ".png")
        ).build());
    }
}

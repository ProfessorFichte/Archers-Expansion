package com.archers_expansion.client.armor;

import com.archers_expansion.items.armors.TundraArcherArmor;
import mod.azure.azurelibarmor.model.GeoModel;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class TundraArcherModel extends GeoModel<TundraArcherArmor> {
    @Override
    public Identifier getModelResource(TundraArcherArmor object) {
        return new Identifier(MOD_ID, "geo/tundra_hunter.geo.json");
    }

    @Override
    public Identifier getTextureResource(TundraArcherArmor armor) {
        var texture = armor.customMaterial.name();
        return new Identifier(MOD_ID, "textures/armor/" + texture +".png");
    }

    @Override
    public Identifier getAnimationResource(TundraArcherArmor animatable) {
        return null;
    }
}

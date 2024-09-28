package com.archers_expansion.client.armor;

import com.archers_expansion.items.armors.WarArcherArmor;
import mod.azure.azurelibarmor.model.GeoModel;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class WarArcherModel extends GeoModel<WarArcherArmor> {
    @Override
    public Identifier getModelResource(WarArcherArmor object) {
        return new Identifier(MOD_ID, "geo/war_archer.geo.json");
    }

    @Override
    public Identifier getTextureResource(WarArcherArmor armor) {
        return new Identifier(MOD_ID, "textures/armor/war_archer.png");
    }

    @Override
    public Identifier getAnimationResource(WarArcherArmor animatable) {
        return null;
    }
}

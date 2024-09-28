package com.archers_expansion.client.armor;

import com.archers_expansion.items.armors.DeadeyeArmor;
import mod.azure.azurelibarmor.model.GeoModel;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class DeadeyeModel extends GeoModel<DeadeyeArmor> {
    @Override
    public Identifier getModelResource(DeadeyeArmor object) {
        return new Identifier(MOD_ID, "geo/deadeye.geo.json");
    }

    @Override
    public Identifier getTextureResource(DeadeyeArmor armor) {
        return new Identifier(MOD_ID, "textures/armor/deadeye.png");
    }

    @Override
    public Identifier getAnimationResource(DeadeyeArmor animatable) {
        return null;
    }
}

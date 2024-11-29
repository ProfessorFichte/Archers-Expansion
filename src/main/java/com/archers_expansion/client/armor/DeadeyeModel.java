package com.archers_expansion.client.armor;

import com.archers_expansion.items.armors.DeadeyeArmor;
import mod.azure.azurelibarmor.common.api.client.model.GeoModel;
import net.minecraft.util.Identifier;

import static com.archers_expansion.ArchersExpansionMod.MOD_ID;

public class DeadeyeModel extends GeoModel<DeadeyeArmor> {
    @Override
    public Identifier getModelResource(DeadeyeArmor object) {
        return Identifier.of(MOD_ID, "geo/deadeye.geo.json");
    }

    @Override
    public Identifier getTextureResource(DeadeyeArmor armor) {
        var textureId = armor.getFirstLayerId();
        return Identifier.of(MOD_ID, "textures/armor/"+ textureId.getPath() + ".png");
    }

    @Override
    public Identifier getAnimationResource(DeadeyeArmor animatable) {
        return null;
    }
}

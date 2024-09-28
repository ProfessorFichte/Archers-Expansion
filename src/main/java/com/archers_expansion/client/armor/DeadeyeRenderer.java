package com.archers_expansion.client.armor;

import com.archers_expansion.items.armors.DeadeyeArmor;
import mod.azure.azurelibarmor.renderer.GeoArmorRenderer;

public class DeadeyeRenderer extends GeoArmorRenderer<DeadeyeArmor> {
    public DeadeyeRenderer() {
        super(new DeadeyeModel());
    }
}

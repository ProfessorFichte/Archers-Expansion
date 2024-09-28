package com.archers_expansion.client.armor;

import com.archers_expansion.items.armors.WarArcherArmor;
import mod.azure.azurelibarmor.renderer.GeoArmorRenderer;

public class WarArcherRenderer extends GeoArmorRenderer<WarArcherArmor> {
    public WarArcherRenderer() {
        super(new WarArcherModel());
    }
}

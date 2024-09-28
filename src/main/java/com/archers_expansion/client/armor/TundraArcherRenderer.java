package com.archers_expansion.client.armor;

import com.archers_expansion.items.armors.TundraArcherArmor;
import mod.azure.azurelibarmor.renderer.GeoArmorRenderer;

public class TundraArcherRenderer extends GeoArmorRenderer<TundraArcherArmor> {
    public TundraArcherRenderer() {
        super(new TundraArcherModel());
    }
}

package com.archers_expansion.config;

import com.archers_expansion.items.armors.Armors;
import net.spell_engine.api.item.ItemConfig;

public class Default {
    public final static ItemConfig itemConfig;
    static {
        itemConfig = new ItemConfig();
        for (var armorSet : Armors.entries) {
            itemConfig.armor_sets.put(armorSet.name(), armorSet.defaults());
        }
    }
}
